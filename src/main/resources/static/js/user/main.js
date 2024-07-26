

const header = document.querySelector('header');

const mainMenuLies = document.querySelectorAll('#main-menu-nav li');
const subMenuLies = document.querySelectorAll('#sub-menu-section > ul > li');

for (let i = 0; i < mainMenuLies.length; i++) {
    mainMenuLies[i].addEventListener('mouseover', () => {
        // 모든 카테고리를 눈에 안보이게 한다
        subMenuLies.forEach(subMenuLi => {
            subMenuLi.toggleAttribute('active', false);
        });
        // i 번째 카테고리만 눈에 보이도록 만든다
        subMenuLies.item(i).toggleAttribute('active', true);
    });
}
// 마우스가 헤더 영역에서 벗어난다면
header.addEventListener('mouseleave', () => {
    // 모든 카테고리를 눈에 안보이게 한다
    subMenuLies.forEach(subMenuLi => {
        subMenuLi.toggleAttribute('active', false);
    });
})

