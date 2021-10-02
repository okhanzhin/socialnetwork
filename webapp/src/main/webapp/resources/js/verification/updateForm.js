$(function () {
    const updateForm = document.getElementById('form');
    const surname = document.getElementById('surname');
    const name = document.getElementById('name');
    const skype = document.getElementById('skype');
    const icq = document.getElementById('icq');

    updateForm.addEventListener('submit', submitForm);

    function submitForm(e) {
        e.preventDefault();
        isDataValid = true;

        checkRequired(surname);

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
    }
});