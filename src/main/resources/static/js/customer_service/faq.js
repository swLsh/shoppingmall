const aTags  = document.getElementsByClassName('top10-atag');
const modalSection = document.querySelector('.modal');
const modalButton = document.querySelector('.modal-button');
for (const aTagElement of aTags) {
    console.log(aTagElement)
    aTagElement.onclick = () => {
        if(modalSection.style.display === 'none'){
            modalSection.style.display = 'block';
        }
        modalSection.toggleAttribute('active', true);
    }
}

modalButton.onclick = () => {
    modalSection.style.display = 'none';
}


