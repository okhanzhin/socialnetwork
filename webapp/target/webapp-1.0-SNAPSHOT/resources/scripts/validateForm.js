const MIN_NAME_LENGTH = 2;
const MAX_NAME_LENGTH = 15;
const MIN_PASSWORD_LENGTH = 1;
const MAX_PASSWORD_LENGTH = 30;
const MIN_ICQ_LENGTH = 5;
const MAX_ICQ_LENGTH = 9;

(function () {
    const form = document.getElementById("form");
    const surname = document.getElementById("surname");
    const name = document.getElementById("name");
    const password = document.getElementById("password");
    const icq = document.getElementById("icq");
    let isDataValid;

    form.addEventListener("submit", submitForm);

    function submitForm(e) {
        e.preventDefault();
        isDataValid = true;

        checkRequired(surname);

        if (checkRequired(name)) {
            checkLength(name, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        }

        if (checkRequired(password)) {
            if (checkLength(password, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH)) {
                checkContainsOnlyDigits(password);
            }
        }

        if (checkLength(icq, MIN_ICQ_LENGTH, MAX_ICQ_LENGTH)) {
            checkContainsOnlyDigits(icq);
        }

        if (isDataValid) {
            showFinalPopUp(e);
        }
    }

    function checkRequired(input) {
        if (input.value.trim() === "") {
            showError(input, getFieldName(input.name) + " is required.");
            isDataValid = false;
            return false;
        } else {
            showSuccess(input);
            return true;
        }
    }

    function checkLength(input, min, max) {
        if (input.value.length < min) {
            showError(input, getFieldName(input.name) + " must be at least " + min + " characters.");
            isDataValid = false;
            return false;
        } else if (input.value.length > max) {
            showError(input, getFieldName(input.name) + " must be less than " + max + " characters.");
            isDataValid = false;
            return false;
        } else {
            showSuccess(input);
            return true;
        }
    }

    function checkContainsOnlyDigits(input) {
        let onlyNumbersRegExp = RegExp('^[0-9]*$');
        if (!onlyNumbersRegExp.test(String(input.value))) {
            showError(input, getFieldName(input.name) + " must contains only digits.");
            isDataValid = false;
            return false;
        } else {
            showSuccess(input);
            return true;
        }
    }

    function showFinalPopUp(e) {
        if (confirm('Are you sure?')) {
            e.target.submit();
        } else {
            return false;
        }
    }

    function getFieldName(inputName) {
        return inputName[0].toUpperCase() + inputName.slice(1);
    }

    function showError(input, message) {
        const inputGroup = input.parentElement;
        inputGroup.className = "input-group input-group-sm border border-danger mb-4"
        const small = inputGroup.querySelector("small");
        small.classList.add("visible");
        small.innerText = message;
    }

    function showSuccess(input) {
        const inputGroup = input.parentElement;
        inputGroup.className = "input-group input-group-sm border border-success mb-3";
        const small = inputGroup.querySelector("small");
        small.classList.remove("visible");
    }
})();

// function isValidEmail(input) {
//     const emailRegExp = RegExp('[^@]+@[^\.]+\..+');
//     if (!emailRegExp.test(String(input.value.trim()).toLowerCase())) {
//         showError(input, getFieldName(input) + " invalid.");
//     } else {
//         showSuccess(input);
//     }
// }