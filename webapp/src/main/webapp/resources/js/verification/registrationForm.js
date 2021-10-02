$(function () {
    const registrationForm = document.getElementById('form')
    const surname = document.getElementById('surname');
    const name = document.getElementById('name');
    const email = document.getElementById('email');
    const password = document.getElementById("password");
    const skype = document.getElementById('skype');
    const icq = document.getElementById('icq');

    registrationForm.addEventListener('submit', submitForm);

    async function submitForm(e) {
        e.preventDefault();
        isDataValid = true;

        if (checkRequired(email)) {
            if (checkEmailValidForm(email)) {
                try {
                    await checkEmailExist(email);

                    if (checkRequired(password)) {
                        checkLength(password, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
                    }

                    if (checkRequired(surname)) {
                        checkLength(surname, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
                    }

                    if (checkRequired(name)) {
                        checkLength(name, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
                    }

                    if (skype.value.trim() !== EMPTY_LINE) {
                        checkContainsOnlyDigits(skype);
                    }

                    if (icq.value.trim() !== EMPTY_LINE) {
                        if (checkContainsOnlyDigits(icq)) {
                            checkLength(icq, MIN_ICQ_LENGTH, MAX_ICQ_LENGTH);
                        }
                    }

                    if (isDataValid) {
                        showFinalPopUp(e);
                    }
                } catch (e) {
                    console.log(e);
                }
            }
        }
    }
});