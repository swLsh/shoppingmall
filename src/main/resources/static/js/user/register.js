const [registerBtn, cancelBtn] = document.getElementsByClassName('regist')

const emailHead = document.getElementById('email-input-addr').querySelector('input')
const emailBody = document.getElementById('email-input-domain-manual').querySelector('input')
const emailTag = document.getElementById('email')
const emailChoose = document.getElementById('email-input-domain-auto').querySelector('select');

const phoneHead = document.getElementById('phone-input-head').querySelector('select')
const phoneMiddle = document.getElementById('phone-input-middle').querySelector('input')
const phoneTail = document.getElementById('phone-input-tail').querySelector('input')
const phoneTag = document.getElementById('phone')

const certificationBtn = document.getElementById('certification-Btn');
const impUidInput = document.getElementById('imp-uid');

IMP.init("imp88732243");
// 본인인증 요청
certificationBtn.onclick = () => {
// IMP.certification(param, callback) 호출
    IMP.certification(
        { pg: "inicis_unified.MIIiasTest" },
        function (response) {
            if (response.success) {
                impUidInput.value = response["imp_uid"];
                certificationBtn.textContent = "본인인증완료";
                certificationBtn.disabled = true;
            } else {
                alert('본인인증이 완료되지 못했습니다');
            }
        },
    );
}




emailChoose.onchange = () => {
    if(emailChoose.value === '직접입력') {
        emailBody.readOnly = false;
    }else{
        emailBody.readOnly = true;
        emailBody.value = emailChoose.value;
    }
}

registerBtn.onclick = () => {
    emailTag.value = emailHead.value + '@' + emailBody.value;
    phoneTag.value = `${phoneHead.value}-${phoneMiddle.value}-${phoneTail.value}`;

    document.forms.item(0).submit();

}

cancelBtn.onclick = () => {
    location.href = '/user/login';
}
