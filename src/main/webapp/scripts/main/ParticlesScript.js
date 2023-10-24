function getRandomNumber(min, max) {
    return Math.random() * (max - min) + min;
}

function createParticle() {
    const particle = document.createElement("div");
    particle.classList.add("particle");

    let startX = getRandomNumber(0, window.innerWidth);
    let startY = getRandomNumber(0, window.innerHeight);

    const speedX = getRandomNumber(-1, 1);
    const speedY = getRandomNumber(-1, 1);

    particle.style.left = startX + "px";
    particle.style.top = startY + "px";

    document.querySelector(".particle-container").appendChild(particle);

    function moveParticle() {
        startX += speedX;
        startY += speedY;
        if (startX < 5 || startX > window.innerWidth - 5 || startY < 5 || startY > window.innerHeight - 5) {
            particle.remove();
        } else {
            particle.style.left = startX + "px";
            particle.style.top = startY + "px";
            requestAnimationFrame(moveParticle);
        }
    }

    moveParticle();
}

setInterval(createParticle, 100);