package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MemberRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PictureRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RequestRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.CommunityTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.CommunityPicture;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.Request;
import com.getjavajob.training.okhanzhin.socialnetwork.service.utils.TransferEntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CommunityService {
    public static final Member.Status OWNER = Member.Status.OWNER;
    public static final Member.Status MODERATOR = Member.Status.MODERATOR;
    public static final Member.Status USER = Member.Status.USER;
    public static final Logger logger = LoggerFactory.getLogger(CommunityService.class);

    private final AccountRepository accountRepository;
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    private final RequestRepository requestRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    public CommunityService(AccountRepository accountRepository,
                            CommunityRepository communityRepository,
                            MemberRepository memberRepository,
                            RequestRepository requestRepository,
                            PictureRepository pictureRepository) {
        this.accountRepository = accountRepository;
        this.communityRepository = communityRepository;
        this.memberRepository = memberRepository;
        this.requestRepository = requestRepository;
        this.pictureRepository = pictureRepository;
    }

    private Community getEntity(CommunityTransfer transfer) {
        TransferEntityConverter<CommunityTransfer, Community> converter = new TransferEntityConverter<>();

        return Objects.requireNonNull(converter.buildEntity(transfer, Community.class));
    }

    public CommunityTransfer getTransfer(Community community) {
        TransferEntityConverter<Community, CommunityTransfer> converter = new TransferEntityConverter<>();
        CommunityTransfer transfer = Objects.requireNonNull(converter.buildTransfer(community, CommunityTransfer.class));
        transfer.setPicture(pictureRepository.getCommunityPicture(community).getContent());

        return transfer;
    }

    @Transactional
    public Community create(CommunityTransfer transfer) {
        if (!transfer.isPicAttached()) {
            transfer.setPicture(new byte[0]);
        }

        Community community = getEntity(transfer);
        community.setDateOfRegistration(LocalDate.now());

        CommunityPicture picture = new CommunityPicture(transfer.getPicture());
        picture.setCommunity(community);

        pictureRepository.create(picture);
        logger.info("Community ID {} has been created.", community.getCommID());

        return community;
    }

    @Transactional
    public Community update(CommunityTransfer transfer) {
        Community storedCommunity = communityRepository.getById(transfer.getCommID());

        if (transfer.isPicAttached()) {
            CommunityPicture storedPicture = (CommunityPicture) pictureRepository.getCommunityPicture(storedCommunity);
            if (!Arrays.equals(storedPicture.getContent(), transfer.getPicture())) {
                storedPicture.setContent(transfer.getPicture());
                pictureRepository.update(storedPicture);
            }
        }

        Community updatedCommunity = updateFromTransfer(storedCommunity, transfer);
        logger.info("Community ID {} has been updated.", updatedCommunity.getCommID());

        return communityRepository.update(updatedCommunity);
    }

    private Community updateFromTransfer(Community community, CommunityTransfer transfer) {
        community.setCommunityName(transfer.getCommunityName());
        community.setCommDescription(transfer.getCommDescription());
        if (transfer.isPicAttached()) {
            community.setPicAttached(transfer.isPicAttached());
        }

        return community;
    }

    @Transactional
    public void delete(Community community) {
        communityRepository.deleteById(community.getCommID());
    }

    public Community getById(long commID) {
        return communityRepository.getById(commID);
    }

    public List<Community> getAccountCommunities(Account account) {
        return communityRepository.getAccountCommunities(account);
    }

    @Transactional
    public void createOwner(Account account, Community community) throws ServiceException {
        Member owner = new Member(account, community, OWNER);
        memberRepository.create(owner);
    }

    public Member getOwner(Community community) {
        return memberRepository.getOwnerByCommId(community.getCommID());
    }

    @Transactional
    public void setOwner(long accountID, long commID) throws ServiceException {
        Member member = memberRepository.getOwnerByCommId(commID);

        if (member.getAccount().getAccountID() != accountID) {
            member.setAccount(accountRepository.getById(accountID));
            memberRepository.update(member);
        } else {
            throw new ServiceException("Current account is already the owner of the group");
        }
    }

    @Transactional
    public void setModerator(long accountID, long commID) throws ServiceException {
        Member member = getMember(accountID, commID);
        List<Member> moderators = memberRepository.getCommunityModerators(commID);

        if (!moderators.contains(member)) {
            member.setMemberStatus(MODERATOR);
            memberRepository.update(member);
        } else {
            throw new ServiceException("Current account is already the moderator of the group");
        }
    }

    @Transactional
    public void setUser(long accountID, long commID) throws ServiceException {
        Member member = getMember(accountID, commID);
        member.setMemberStatus(USER);
        memberRepository.update(member);
    }

    @Transactional
    public Member createMember(long accountID, long commID) throws ServiceException {
        Member member = new Member(accountRepository.getById(accountID), communityRepository.getById(commID), USER);
        List<Member> users = memberRepository.getCommunityMembers(commID);

        if (!users.contains(member)) {
            return memberRepository.create(member);
        } else {
            return null;
        }
    }

    public Member getMember(long accountID, long commID) {
        return memberRepository.getMember(accountRepository.getById(accountID), communityRepository.getById(commID));
    }

    @Transactional
    public void deleteMemberFromCommunity(long accountID, long commID) throws ServiceException {
        Member member = getMember(accountID, commID);
        List<Member> users = memberRepository.getCommunityMembers(commID);

        if (users.contains(member)) {
            memberRepository.deleteMember(accountRepository.getById(accountID), communityRepository.getById(commID));
        }
    }

    public List<Member> getListOfMembers(long commID) {
        return memberRepository.getCommunityMembers(commID);

    }

    public List<Member> getListOfModerators(long commID) {
        return memberRepository.getCommunityModerators(commID);
    }

    public boolean isAbleToModify(long accountID, long groupID) {
        List<Member> members = getListOfMembers(groupID);
        boolean isAbleToModify = false;

        for (Member member : members) {
            if (member.getAccount().getAccountID() == accountID &&
                    (member.getMemberStatus() == OWNER ||
                            member.getMemberStatus() == MODERATOR)) {
                isAbleToModify = true;
                break;
            }
        }

        return isAbleToModify;
    }

    public List<Account> convertMembersToAccounts(List<Member> members) {
        List<Account> accounts = new ArrayList<>();

        if (!members.isEmpty()) {
            for (Member member : members) {
                accounts.add(member.getAccount());
            }
        }

        return accounts;
    }

    public List<Account> getUnconfirmedAccountsForCommunity(Community community) {
        List<Request> communityRequests = requestRepository.getCommunityRequests(community);
        List<Account> accounts = new ArrayList<>();

        for (Request request : communityRequests) {
            accounts.add(request.getSource());
        }

        return accounts;
    }

    public Map<String, Object> configureCommunityPermissions(Account account, Community community) {
        Map<String, Object> groupPermissions = new HashMap<>();
        groupPermissions.put("visitorID", account.getAccountID());

        Member visitorMember = getMember(account.getAccountID(), community.getCommID());
        if (visitorMember != null) {
            groupPermissions.put("visitorStatus", visitorMember.getMemberStatus().getStatus());
        }

        List<Account> unconfirmedRequestAccounts = getUnconfirmedAccountsForCommunity(community);
        groupPermissions.put("isAbleToModify", isAbleToModify(account.getAccountID(), community.getCommID()));

        boolean isMember;
        boolean isSubscribed;

        if (unconfirmedRequestAccounts.contains(account)) {
            isMember = false;
            isSubscribed = true;
        } else {
            isSubscribed = false;
            isMember = visitorMember != null;
        }

        groupPermissions.put("isMember", isMember);
        groupPermissions.put("isSubscribed", isSubscribed);

        return groupPermissions;
    }
}
