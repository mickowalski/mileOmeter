/*******************
 MOBILE NAVIGATION (HAMBURGER MENU)
 **********************/

function hamburgerFunction() {
    var x = document.getElementById("navbar-extra");
    if (x.className === "js--main-nav") {
        x.className += "js--main-nav-responsive";
    } else {
        x.className = "js--main-nav";
    }
}


// Klikam w Hamburgera to zmienia się w X; klikam w X, to zmienia się w Hamburgera

function classToggle() {
    this.classList.toggle('ion-ios-menu');
    this.classList.toggle('ion-ios-close');
}


// // Klikam w ikonkę i uruchamiam funkcję hamburgerFunction


/* Jezeli klikam w ikonę, KIEDY JEST HAMBURGEREM, zmenia się w X i pojawia się rozwijane menu */
function hamburgerFunction() {
    //jeżeli klikam w hamburgera to zamienia się w X oraz wysuwa się menu
    var x = document.querySelector('.icon');
    if (x.ClassName === 'ion-ios-menu') {
        document.querySelector('.js--main-nav').style.display = 'block';
    } else {
        document.querySelector('.js--main-nav').style.display = 'none';
    }
    classToggle();
}

document.querySelector('.icon').addEventListener('click', hamburgerFunction);