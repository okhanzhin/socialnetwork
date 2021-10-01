(function () {
    document.getElementById("addButton").onclick = createPhoneItem;

    for (let deleteButton of document.getElementsByClassName("delete-button")) {
        deleteButton.addEventListener("click", deletePhoneItem, false);
    }

    function createPhoneItem() {
        let phoneInput = document.getElementById("phoneInput");
        if (document.getElementById("error") !== null) {
            document.getElementById("error").remove();
            phoneInput.classList.remove("invalid");
        }

        if (isValid(phoneInput.value)) {
            createDomObject(phoneInput.value);
            document.getElementById("phoneInput").value = '';
        } else {
            createErrorObject(phoneInput);
        }
    }

    function isValid(phoneNumber) {
        let phoneRegExp = RegExp('^((\\+7|8)+([0-9]){10})$');
        return phoneRegExp.test(phoneNumber);
    }

    function createDomObject(phoneNumber) {
        let phoneList = document.getElementById("list");

        let groupItem = document.createElement("div");
        groupItem.className = "input-group mb-3";
        let dFlex = document.createElement("div");
        dFlex.className = "d-flex w-100";
        let phoneType = document.getElementById("phoneType").value;

        let typeLabel = document.createElement("label");
        typeLabel.className = "input-group-text";
        let typeLabelText = document.createTextNode(phoneType[0].toUpperCase() + phoneType.slice(1) + " Phone");
        let inputString = document.createElement("input");
        inputString.type = "text";
        inputString.className = "form-control";
        inputString.readOnly = true;
        inputString.name = phoneType + "Phone";
        inputString.value = phoneNumber;
        let deleteButton = document.createElement("button");
        deleteButton.type = "button";
        deleteButton.className = "btn btn-edit-phone";
        let deleteText = document.createTextNode("Delete");

        deleteButton.appendChild(deleteText);
        deleteButton.addEventListener("click", deletePhoneItem, false);
        typeLabel.appendChild(typeLabelText);
        dFlex.appendChild(typeLabel);
        dFlex.appendChild(inputString);
        dFlex.appendChild(deleteButton);
        groupItem.appendChild(dFlex);
        phoneList.append(groupItem);
    }

    function createErrorObject(phoneInput) {
        phoneInput.classList.add("invalid");
        let error = document.createElement("div");
        error.id = "error";
        error.innerHTML = "Please enter valid phone number.";
        phoneInput.parentElement.parentElement.append(error);
    }

    function deletePhoneItem(event) {
        console.log("deletePhoneItem");
        event.preventDefault();
        let phoneItem = event.target;
        phoneItem.parentElement.parentElement.remove();
    }
})();




