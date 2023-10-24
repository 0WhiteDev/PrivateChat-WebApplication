window.addEventListener("load", function() {
    const container = document.querySelector(".container");
    const background = document.querySelector(".background");
    const view = document.querySelector(".first-view-section");


    if (container) {
        container.classList.add("show");
    }
    if (background) {
        background.classList.add("show");
    }
    if (view) {
        view.classList.add("show");
    }
});