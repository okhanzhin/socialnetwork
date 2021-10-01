package com.getjavajob.training.okhanzhin.socialnetwork.domain.dto;

import java.util.Objects;

public class SearchEntry {
    private String name;
    private String entryUrl;

    public SearchEntry(String name, String entryUrl) {
        this.name = name;
        this.entryUrl = entryUrl;
    }

    public SearchEntry(String name, String type, String entryUrl, long entryID) {
        this.name = name;
        this.entryUrl = entryUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntryUrl() {
        return entryUrl;
    }

    public void setEntryUrl(String entryUrl) {
        this.entryUrl = entryUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchEntry that = (SearchEntry) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(entryUrl, that.entryUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, entryUrl);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("SearchEntry{").
                append("name='").append(name).append('\'').
                append(", url='").append(entryUrl).append('\'').
                append('}').toString();
    }
}
