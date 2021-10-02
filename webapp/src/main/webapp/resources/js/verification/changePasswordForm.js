$(function () {
    const passwordForm = document.getElementById('password-form');
    const storedPassword = document.getElementById('stored-password');
    const newPassword = document.getElementById('new-password');
    const confirmPassword = document.getElementById('confirm-password');

    passwordForm.addEventListener('submit', submitPasswordForm);

    async function submitPasswordForm(e) {
        e.preventDefault();
        isDataValid = true;

        if (checkRequired(storedPassword)) {
            try {
                await checkPasswordCorrect(storedPassword);

                if (checkRequired(newPassword)) {
                    checkLength(newPassword, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
                }

                if (checkRequired(confirmPassword)) {
                    if (checkLength(confirmPassword, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH)) {
                        checkConfirm(newPassword, confirmPassword);
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
});