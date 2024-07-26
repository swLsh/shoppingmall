// import {header} from "/js/public/header.js";


const orderForm = document.getElementById('order-form');
const [minusBtn, plusBtn] = document.querySelectorAll(".amount-label > button");
const amountInput = document.querySelector(".amount-label > input");
const cartBtn = document.getElementById('cart-btn');
const buyBtn = document.getElementById('buy-btn');

const selectButtonsContainer = document.getElementsByClassName('select-buttons');
console.log(selectButtonsContainer);
console.log(cartBtn);
// 버튼 클릭 시 버튼 on/off 와 input 값 설정
[...selectButtonsContainer].forEach(buttonContainer => {
    const buttons = buttonContainer.querySelectorAll('button');
    console.log(buttons)
    buttons.forEach(button => {
        button.onclick = () => {
            const inputTag = buttonContainer.querySelector('input');
            console.log(inputTag);
            console.log(button)
            buttons.forEach(button => {
                button.toggleAttribute('selected', true);
            });
            button.toggleAttribute('selected', false);
            inputTag.value = button.textContent;
        }
    });
});

// 수량 변경
minusBtn.onclick = () => {
    const amount = +amountInput.value;
    if(amount <= 1){
        amountInput.value = 1;
    }else{
        amountInput.value = amount - 1;
    }
}
plusBtn.onclick = () => {
    const amount = +amountInput.value;
    amountInput.value = amount + 1;
}
// 장바구니 버튼
cartBtn.onclick = () => {
    if(check_input()){
        const data = new FormData(orderForm);
        const csrfToken = data.get('_csrf');
        fetch(`/user/cart`, {
            method: 'POST',
            headers: {"X-CSRF-TOKEN": csrfToken},
            body: data
        }).then(response => {
            switch(response.status){
                case 201:
                    alert("장바구니에 상품을 추가하였습니다");
                    break;
                case 401:
                    alert("로그인이 필요합니다");
                    break;
                default:
                    alert('알 수 없는 에러가 발생했습니다. 관리자에게 문의해주세요')
            }
        })
    }
}

function  check_input(){
    const colorInput = document.querySelector('.colors > input');
    const sizeInput = document.querySelector('.sizes > input');
    if(colorInput.value === undefined || colorInput.value === ''){
        alert('색상을 선택해주세요');
        return false;
    }
    if(sizeInput.value === undefined || sizeInput.value === ''){
        alert('사이즈를 선택해주세요');
        return false;
    }
    if(+amountInput.value < 1 || !/^[0-9]+$/g.test(amountInput.value)){
        alert('수량을 정확히 입력해 주세요');
        return false;
    }
    return true;

}



