document.addEventListener("DOMContentLoaded", () =>  {
    const increaseButtons = document.querySelectorAll(".increase-btn");
    const decreaseButtons = document.querySelectorAll(".decrease-btn");

    let changeQuantity = (input, valueToChange) => {
        let currentValue = parseInt(input.value);
        if (currentValue + valueToChange >= 1) {
            input.value = currentValue + valueToChange;
        }
    }

    increaseButtons.forEach(button => {
        button.addEventListener("click", e => {
            e.preventDefault();
            const form = e.target.closest("form");
            const countElement = form.getElementsByClassName("count")[0];
            changeQuantity(countElement, 1);
        });
    });

    decreaseButtons.forEach(button => {
        button.addEventListener("click", e => {
            e.preventDefault();
            const form = e.target.closest("form");
            const countElement = form.getElementsByClassName("count")[0];
            changeQuantity(countElement, -1);
        })
    });

    // const profileLink = document.getElementById("profile-link");
    // const modal = document.getElementById("modal");
    // const closeButton = document.getElementById("close-button");
    //
    // if(profileLink && modal && closeButton) {
    //     profileLink.addEventListener("click", e => {
    //         e.preventDefault();
    //         modal.style.display = "block";
    //     });
    //
    //     closeButton.addEventListener("click", e => {
    //         e.preventDefault();
    //         modal.style.display = "none";
    //     });
    //
    //     document.addEventListener("click", e =>  {
    //         if(e.target === modal) {
    //             modal.style.display = "none";
    //         }
    //     });
    // }

});