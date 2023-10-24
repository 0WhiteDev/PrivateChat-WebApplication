window.addEventListener("load", function() {
    initAnimation()
});

function initAnimation() {
    const controller = new ScrollMagic.Controller();

    const panel = document.querySelector(".panel");
    const container = document.querySelectorAll(".container");

    const tl = new TimelineMax();

    tl.from(panel, 1, { x: 300, opacity: 0 });
    tl.staggerFrom(container, 0.5, { x: 300, opacity: 0 }, 0.2);

    const scene = new ScrollMagic.Scene({
        triggerElement: ".features-section",
        triggerHook: 0.7,
        reverse: false,
    })
        .setTween(tl)
        .addTo(controller);
}