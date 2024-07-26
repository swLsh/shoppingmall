const lockLabel = document.getElementById('lock');
const [openBtn, lockBtn] = document.querySelectorAll('input[type=radio]');
const filePlusBtn = document.getElementById('file-plus-btn');
const filesContainerSection = document.getElementById('files-container');
const filesDivClone = document.querySelector('.files').cloneNode(true);

function remove_file_div(fileRemoveBtn){
    // 제거 버튼의 부모(div)를 제거한다.
    fileRemoveBtn.parentElement.remove();
}

/****************** 공개 / 비공개 *********************/
openBtn.onclick = () => {
    lockLabel.removeAttribute('active');
    lockLabel.querySelector('input').disabled = true;
}
lockBtn.onclick = () => {
    lockLabel.setAttribute('active', '');
    lockLabel.querySelector('input').disabled = false;
}

/****************** 파일 첨부 *********************/
filePlusBtn.onclick = () => {
    filesContainerSection.appendChild(filesDivClone.cloneNode(true));
}









