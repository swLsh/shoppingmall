const orderUserNameInput = document.getElementById('order-user');
const [orderPhone1,orderPhone2,orderPhone3] = document.querySelectorAll('.order-phone input');
const orderFindAddrBtn = document.getElementById('order-find-addr-btn');
const orderPostCodeInput = document.getElementById('order-addr-number');
const orderAddressInput = document.getElementById('order-addr');
const orderAddrDetailsInput = document.getElementById('order-addr-details');
const orderMemoInput = document.getElementById('order-memo');

const products = document.getElementsByClassName('product');
const creditCardSection = document.querySelector('.credit-card-section');
const simplePaymentSection = document.querySelector('.simple-payment-section');
const simplePayments = simplePaymentSection.getElementsByTagName('label');
const orderBuyBtn = document.getElementById('order-buy-btn');
const totalPrice = document.getElementById('total-price');

// 주소 찾기 클릭 시
orderFindAddrBtn.onclick = () => {
    new daum.Postcode({
        oncomplete: function(data) {
            const postcode = data.zonecode; // (우편번호)
            const address = data.address; // (도로명 주소)
            const buildingName = data.buildingName; // 건물명(아파트 이름 등.)
            orderPostCodeInput.value = postcode;
            orderAddressInput.value = address;
            if(buildingName.trim().length > 0){
                orderAddressInput.value += `(${buildingName})`;
            }
        }
    }).open();
}
// 신용카드 클릭 시
creditCardSection.onclick = () => {
    creditCardSection.querySelector('i').className = 'fa-solid fa-circle';
    simplePaymentSection.querySelector('i').className = 'fa-regular fa-circle';
}
// 신용카드 클릭 시
simplePaymentSection.onclick = () => {
    simplePaymentSection.querySelector('i').className = 'fa-solid fa-circle';
    creditCardSection.querySelector('i').className = 'fa-regular fa-circle';
}
// 간편결제 버튼 하나 클릭 시
[...simplePayments].forEach(simplePayment => {
    simplePayment.addEventListener('click', (e) => {
        [...simplePayments].forEach(simplePayment => {
            simplePayment.toggleAttribute('active', false);
        });
        simplePayment.toggleAttribute('active', true);
    });
});

IMP.init('imp88732243');
// 결제 버튼 클릭 시
orderBuyBtn.onclick = () => {
    let paymentMethod;
    [...simplePayments].forEach(simplePayment => {
        if(simplePayment.hasAttribute('active')){
            paymentMethod = simplePayment.id;
        }
    });

    const paymentData = create_order_data_object(paymentMethod);

    IMP.request_pay(paymentData, function (response){
        const csrfToken = document.querySelector('meta[name=_csrf]').getAttribute('content');
        const impUid = response.imp_uid;
        const merchantUid = response.merchant_uid;
        const paidAmount = response.paid_amount;
        const paidAt = response.paid_at;
        const payMethod = response.pay_method;
        const productInfo = [...products].map(product => {
            return {
                no: product.querySelector('.product-no').value,
                price: product.querySelector('.price').getAttribute('data'),
                amount: product.querySelector('.amount').getAttribute('data'),
                color: product.querySelector('.color').textContent,
                size: product.querySelector('.size').textContent,
            };
        });
        const memo = orderMemoInput.value;
        const orderObject = {
            id: merchantUid, // 상점에서 생성한 고유 주문번호
            products: productInfo,
            title: paymentData.name,
            receiverName: paymentData.buyer_name,
            receiverPhone: paymentData.buyer_tel,
            receiverAddress: paymentData.buyer_addr,
            receiverPostcode: paymentData.buyer_postcode,
            memo: memo,
            impUid: impUid,
            payMethod:payMethod,
            paidAmount: paidAmount,
            paidAt: paidAt
        };

        fetch(`/user/payment`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-CSRF-TOKEN": csrfToken
            },
            body: JSON.stringify(orderObject)
        }).then(response => {
            if(response.ok){
                const cartNumbers = [];
                for (let i = 0; i < products.length; i++) {
                    const cartNo = products[i].querySelector('.cart-no').value;
                    if(cartNo == null || cartNo === ''){
                        cartNumbers.push([{no: cartNo}]);
                    }
                }
                fetch(`/user/cart`, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                        "X-CSRF-TOKEN": csrfToken
                    },
                    body: JSON.stringify(cartNumbers)
                }).then(response => {
                    if(response.ok){
                        alert('주문 요청이 성공적으로 완료되었습니다!');
                        location.href = '/user/cart';
                    }
                });
            }
        })
    });
}

function create_order_data_object(paymentMethod){
    const merchantUid = crypto.randomUUID().replaceAll('-', '').substring(0, 12); // 주문번호
    let orderName = products.item(0).querySelector('.name').textContent;
    if(orderName.length > 8){
        orderName = orderName.substring(0, 8) + "...";
    }
    // 상품이 여러개이다
    if(products.length > 1){
        orderName += ` 외 (${products.length - 1})건`
    }

    let price = 0;
    for (let i = 0; i < products.length; i++) {
        price += +products[i].querySelector('.price').getAttribute('data');
    }
    const name = orderUserNameInput.value;
    const tel = `${orderPhone1.value}-${orderPhone2.value}-${orderPhone3.value}`;
    const addr = `${orderAddressInput.value} ${orderAddrDetailsInput.value}`;
    const postcode = orderPostCodeInput.value;
    const totalPrice = totalPrice.getAttribute('data');

    const paymentObject = {
        merchant_uid: merchantUid, // 상점에서 생성한 고유 주문번호
        name: orderName,
        amount: totalPrice, // 총 가격
        buyer_name: name,
        buyer_tel: tel,
        buyer_addr: addr,
        buyer_postcode: postcode,
    };

    switch (paymentMethod.toUpperCase()){
        case "KAKAO":
            return kakao_pay(paymentObject);
        case "NAVER":
        // return kakao_pay(paymentObject);
        case "TOSS":
        // return kakao_pay(paymentObject);
    }
}

function kakao_pay(paymentObject){
    return {
        ...paymentObject,
        pg: "kakaopay.TC0ONETIME"
    };
}














