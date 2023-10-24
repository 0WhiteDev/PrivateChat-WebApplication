window.addEventListener("load", function() {
    initAnimation()
});

function initAnimation() {
    const controller = new ScrollMagic.Controller();

    const featuresText = document.querySelector(".features-text");
    const featuresContainer = document.querySelectorAll(".features-container");

    const tl = new TimelineMax();

    tl.from(featuresText, 1, { x: 300, opacity: 0 });
    tl.staggerFrom(featuresContainer, 0.5, { x: 300, opacity: 0 }, 0.2);

    const scene = new ScrollMagic.Scene({
        triggerElement: ".features-section",
        triggerHook: 0.7,
        reverse: false,
    })
        .setTween(tl)
        .addTo(controller);
}