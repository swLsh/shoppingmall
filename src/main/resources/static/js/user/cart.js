const orderForm = document.getElementById('order-form');
const productContainers = document.getElementsByClassName('product');
const csrfTokenInput = orderForm.querySelector('input[name="_csrf"]');
const allSectionBtn = document.querySelector('.cart-select-container input[type="checkbox"]');
const allRmBtn = document.getElementById('cart-all-rm-btn');
const cartBuyBtn = document.getElementById('cart-buy-btn');
// 전체 선택 버튼 눌렀을 때
allSectionBtn.addEventListener('click', () => {
    [...productContainers].forEach(productContainer => {
        const checkBoxInput = productContainer.querySelector('input[type="checkbox"]');
        checkBoxInput.checked = allSectionBtn.checked;
    });
});
// 한개 상품 삭제 버튼 눌렀을 시
[...productContainers].forEach(productContainer => {
    const cartNoInput = productContainer.querySelector('input[name=no]');
    const cartRmBtn = productContainer.querySelector('button');
    cartRmBtn.addEventListener('click', (e) => {
        delete_cart_items([{no: +cartNoInput.value}]);
    });
});
// 선택 삭제 버튼 눌렀을 때
allRmBtn.addEventListener('click', () => {
    const items = collect_cart_selected_items();
    if(items.length <= 0){
        alert('상품을 하나이상 선택해주세요');
        return;
    }
    delete_cart_items(items);
});
// 구매 버튼 눌렀을 때
cartBuyBtn.addEventListener('click', (e) => {
    e.preventDefault();
    const items = collect_cart_selected_items();
    if(items.length <= 0){
        alert('상품을 하나이상 선택해주세요');
        return;
    }
    buy_cart_items(items);
});
// 선택되어있는 상품들을 수집
function collect_cart_selected_items(){
    const items = []; //장바구니 아이템 번호들을 가지는 리스트
    // 모든 장바구니 상품들을 순회한다
    [...productContainers].forEach(productContainer => {
        const checkBoxInput = productContainer.querySelector('input[type="checkbox"]');
        const cartNoInput = productContainer.querySelector('input[name=no]');
        // 상품이 선택되어있다면
        if(checkBoxInput.checked) {
            const productNo = +productContainer.querySelector('.product-no').value;
            const color = productContainer.querySelector('.color').textContent;
            const size = productContainer.querySelector('.size').textContent;
            const amount = +productContainer.querySelector('.amount').textContent.replaceAll('개', '');
            const price = +productContainer.querySelector('.price').textContent.replaceAll(',','').replaceAll('원','');
            items.push({
                no: +cartNoInput.value,
                product: { no: productNo },
                amount: amount,
                color: color,
                size: size,
                price: price
            });
        }
    });
    return items;
}
// 선택되어 있는 장바구니 아이템을 삭제
function delete_cart_items(items){
    fetch(`/user/cart`,{
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrfTokenInput.value
        },
        body: JSON.stringify(items)
    }).then(response => {
        if(response.ok){
            alert('상품을 장바구니에서 제거하였습니다');
            location.reload(); // 화면 새로고침
        }
    });
}
// 장바구니 아이템들을 구매
function buy_cart_items(items){
    fetch(`/user/order`,{
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrfTokenInput.value
        },
        body: JSON.stringify(items)
    }).then(response => {
        if(response.ok){
            location.href = '/user/order';
        }
    });
}



