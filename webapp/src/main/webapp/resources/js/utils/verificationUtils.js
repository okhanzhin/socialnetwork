const MIN_NAME_LENGTH = 2;
const MAX_NAME_LENGTH = 15;
const MIN_PASSWORD_LENGTH = 8;
const MAX_PASSWORD_LENGTH = 30;
const MIN_ICQ_LENGTH = 5;
const MAX_ICQ_LENGTH = 9;
const ONLY_NUMBERS_REG_EXP = RegExp('^[0-9]*$');
const EMPTY_LINE = '';
const EMAIL_REG_EXP = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
let isDataValid;


function checkRequired(input) {
    if (input.value !== undefined) {
        if (input.value.trim() === '') {
            showError(input, `${getFieldName(input.name)} is required.`);
            isDataValid = false;
            return false;
        } else {
            showSuccess(input);
            isDataValid = true;
            return true;
        }
    }
}

function checkLength(input, min, max) {
    if (input.value.length < min) {
        showError(input, `${getFieldName(input.name)} must be at least less than ${min} characters.`);
        isDataValid = false;
        return false;
    } else if (input.value.length > max) {
        showError(input, `${getFieldName(input.name)} must be less than ${max} characters.`);
        isDataValid = false;
        return false;
    } else {
        showSuccess(input);
        isDataValid = true;
        return true;
    }
}

function checkEmailValidForm(input) {
    if (!EMAIL_REG_EXP.test(String(input.value))) {
        showError(input, `${getFieldName(input.name)} address isn't valid.`);
        isDataValid = false;
        return false;
    } else {
        showSuccess(input);
        isDataValid = true;
        return true;
    }
}

function checkContainsOnlyDigits(input) {
    if (input.value.trim() !== '') {
        if (!ONLY_NUMBERS_REG_EXP.test(String(input.value))) {
            showError(input, `${getFieldName(input.name)} must contains only digits.`);
            isDataValid = false;
            return false;
        } else {
            showSuccess(input);
            isDataValid = true;
            return true;
        }
    } else {
        isDataValid = false;
        return false;
    }
}

function checkConfirm(password, confirmPassword) {
    if (password.value !== confirmPassword.value) {
        showError(confirmPassword, `${getFieldName(confirmPassword.name)} does not match.`);
        isDataValid = false;
        return false;
    } else {
        showSuccess(confirmPassword);
        isDataValid = true;
        return true;
    }
}

async function checkPasswordCorrect(input) {
    const isPasswordCorrect = await callbackToPromise({
        type: 'get',
        url: '/account/isStoredPasswordCorrect',
        data: {
            storedPassword: input.value,
        },
        dataType: 'json',
    })

    console.log('Ajax done');
    console.log(`Ajax received ${isPasswordCorrect}`)

    return new Promise((resolve) => {
        if (!isPasswordCorrect) {
            showError(input, `Didn't match stored password.`);
            isDataValid = false;
            resolve(isDataValid);
        } else {
            showSuccess(input);
            isDataValid = true;
            resolve(isDataValid);
        }
    });
}

async function checkEmailExist(input) {
    console.log("Inside Ajax")
    const isEmailExist = await callbackToPromise({
        type: 'get',
        url: '/account/isEmailExist',
        data: {
            email: input.value,
        },
        dataType: 'json'
    })

    console.log('Ajax done');
    console.log(`Ajax received ${isEmailExist}`)

    return new Promise((resolve) => {
        if (isEmailExist) {
            showError(input, `Email is already registered. Please try another one.`);
            isDataValid = false;
            resolve(isDataValid);
        } else {
            showSuccess(input);
            isDataValid = true;
            resolve(isDataValid);
        }
    });
}

function callbackToPromise(params) {
    return new Promise((resolve, reject) => {
        $.ajax({...params, success: (data) => resolve(data), error: (e) => reject(e)});
    });
}


function showFinalPopUp(e) {
    if (confirm('Are you sure?')) {
        e.target.submit();
    } else {
        return false;
    }
}

function getFieldName(inputName) {
    return (inputName[0].toUpperCase() + inputName.slice(1)).replace('-', ' ');
}

function showError(input, message) {
    const inputGroup = input.parentElement;
    inputGroup.className = 'input-group input-group-sm border border-danger mb-4'
    const small = inputGroup.querySelector('small');
    small.classList.add('visible');
    small.innerText = message;
}

function showSuccess(input) {
    const inputGroup = input.parentElement;
    inputGroup.className = 'input-group input-group-sm border border-success mb-3';
    const small = inputGroup.querySelector('small');
    small.classList.remove('visible');
}