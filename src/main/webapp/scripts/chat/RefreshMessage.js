window.setInterval(function() {
    saveScrollPosition()
    let oldMsg =  document.getElementById('refresh').innerHTML;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', window.location.href, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('refresh').innerHTML = xhr.responseText;
            if(oldMsg === document.getElementById('refresh').innerHTML){
                restoreScrollPosition();
            }else{
                let container = document.querySelector('.messages-container');
                container.scrollTop = container.scrollHeight;
            }
        }
    };
    xhr.send();
}, 1000);

function saveScrollPosition() {
    let container = document.querySelector('.messages-container');
    localStorage.setItem('scrollPosition', container.scrollTop.toString());
}

function restoreScrollPosition() {
    let container = document.querySelector('.messages-container');
    let savedScrollPosition = localStorage.getItem('scrollPosition');
    if (savedScrollPosition !== null) {
        container.scrollTop = Number.parseInt(savedScrollPosition);
    }
}

document.addEventListener('DOMContentLoaded', restoreScrollPosition);

window.addEventListener('beforeunload', saveScrollPosition);
