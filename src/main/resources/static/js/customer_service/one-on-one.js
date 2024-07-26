function check_is_opened(postNo, event){
    event.preventDefault();
    fetch(`/customer_service/one_on_one/opened/${postNo}`)
        .then(response => response.json())
        .then(isOpened => {
            if (isOpened) {
                location.href = `/customer_service/view/${postNo}`;
            }else{
                const password = prompt('비밀번호를 입력하세요');
                return fetch(`/customer_service/one_on_one/password/${postNo}?password=${password}`)
            }
        })
        .then(response => response.json())
        .then(isCorrect => {
            if (isCorrect) {
                location.href = `/customer_service/view/${postNo}`;
            }else{
                alert('비밀번호가 일치하지 않습니다!');
            }
        })
}