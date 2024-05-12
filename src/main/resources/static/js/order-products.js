
function addToCart(id) {
    let productImg = document.getElementById("img" + id);
    let imgSrc = productImg.getAttribute("src");
    let imgSrcString = String(imgSrc);
    let productName = document.getElementById("name" + id).innerText;
    let productPrice = document.getElementById("price" + id).innerText;
    productPrice = parseInt(productPrice.replaceAll(' ', '').slice(0, -1));
    let inputStepper = document.getElementById("quantity" + id);
    let productQuantity = parseInt(inputStepper.value);
    let item = {
        id: id,
        img: imgSrcString,
        name: productName,
        price: productPrice,
        quantity: productQuantity,
    };
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    let existingItem = cart.find(cartItem => cartItem.name === item.name);
    if (existingItem) {
        existingItem.quantity += item.quantity;
    } else {
        cart.push(item);
    }
    localStorage.setItem('cart', JSON.stringify(cart));
    submitForm();
}


//buttons + -//
function decrementQuantity(id) {
    let quantityInput = document.getElementById("quantity" + id);
    if (parseInt(quantityInput.value) > 1) {
        quantityInput.value = parseInt(quantityInput.value) - 1;
    }
}
function incrementQuantity(id) {
    let quantityInput = document.getElementById("quantity" + id);
    quantityInput.value = parseInt(quantityInput.value) + 1;
}



function fillCartItems() {
    let cartItemsElement = document.getElementById("cart-items");
    cartItemsElement.innerHTML = "";
    let summ = 0;
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    if (cart.length == 0){
        window.location.href = '/shop';
    }
    for (let ind = 0; ind < cart.length; ind++) {
        let cartItemElement = document.createElement('div');
        cartItemElement.classList.add('oneGood');
        cartItemElement.innerHTML = `
            <div class="card">
                <img src="${cart[ind].img}" alt="${cart[ind].name}">
                <p>${cart[ind].name}</p>
            </div>
            <p class="disc1">${cart[ind].quantity}</p>
            <p class="disc2">${cart[ind].price} ₽</p>
            <div class = "removeAndPrice">
                <p>${cart[ind].quantity * cart[ind].price} ₽</p>
                <button class="remove-btn" onclick="removeItem(${ind})"><i class="fa fa-trash" aria-hidden="true"></i></button>
            </div>`;
        let cartIndex = document.getElementById('cartIndex' + ind);
        cartIndex.value = 564; //parseInt(cart[ind].id);
        let productIndex = document.getElementById('productIndex' + ind);
        productIndex.value = parseInt(cart[ind].id);
        let cartName = document.getElementById('name' + ind);
        cartName.value = cart[ind].name;
        let cartQuantity = document.getElementById('quantity' + ind);
        cartQuantity.value = parseInt(cart[ind].quantity);
        let cartProductPrice = document.getElementById('price' + ind);
        cartProductPrice.value = parseInt(cart[ind].price);
        summ +=  cartQuantity.value * cartProductPrice.value ;
        cartItemsElement.appendChild(cartItemElement);
    }
    let quantitySumm = document.getElementById(`text2`);
    quantitySumm.value = parseInt(quantitySumm.value | 0) + summ;
}

function removeItem(index) {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.splice(index, 1);
    localStorage.setItem('cart', JSON.stringify(cart));
    submitFormOnCart();
    fillCartItems();
}

function removeAll(){
    localStorage.removeItem('cart');
    window.location.href = '/account';
}



function getNumberOfItems() {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    let input = document.getElementById('inputItems');
    input.value = parseInt(cart.length);
}
//
//function submitForm() {
//    getNumberOfItems();
//    var form = document.getElementById("myForm");
//    let input = document.getElementById('inputItems');
//    //let inputvalue = input.value
//    //console.log(inputvalue)
//    form.submit();
//    window.location.href = '/shop';
//}

document.getElementById("myForm2").addEventListener("submit", function(event) {
    event.preventDefault(); // Предотвращение стандартной отправки формы

    // Получение ссылки на форму
    var form = event.target;

    // Отправка формы
    form.submit();

    // Дождемся успешной отправки формы
    form.addEventListener('load', function() {
        // Переход на другую страницу после успешной отправки формы
        window.location.href = '/shopping-cart';
    });
});

function submitFormOnCart() {
    getNumberOfItems();
    var form = document.getElementById("myForm2");
    form.submit();
    window.location.href = '/shopping-cart';
}



document.getElementById("myForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Предотвращение стандартной отправки формы

    // Получение ссылки на форму
    var form = event.target;

    // Отправка формы
    form.submit();

    // Дождемся успешной отправки формы
    form.addEventListener('load', function() {
        // Переход на другую страницу после успешной отправки формы
        window.location.href = '/shop';
    });
});




function submitForm() {
    getNumberOfItems();
    var form = document.getElementById("myForm");
    // Отправка формы
    form.submit();
}