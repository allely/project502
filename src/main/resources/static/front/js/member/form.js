/**
 * 파일 업로드 후 후속 처리 함수
 *
 * @params files : 업로드한 파일 정보 목록
 */
function callbackFileUpload(files) {
    if (!files || files.length == 0) {
        return;
    }
//    console.log(files);   // 파일 잘 업로드됐는지 확인용

    const file = files[0];

    // id = image1_tpl 인 html의 내용 가져오기
    let html = document.getElementById("image1_tpl").innerHTML;

    const imageUrl = file.thumbsUrl.length > 0 ? file.thumbsUrl.pop() : file.fileUrl;
    const seq = file.seq;
    html = html.replace(/\[seq\]/g, seq)
                .replace(/\[imageUrl\]/g, imageUrl);

//    console.log(html);    // html 내용 가져왔는지 확인

    /*
     * DOM : 문서 객체 모델
     * domParser : DOM Document 문서에 맞는 XML 및 HTML 소스 코드를 해석할 수 있는 기반을 제공
     */
    const domParser = new DOMParser();
    const dom = domParser.parseFromString(html, "text/html");
//    console.log(dom);

    const imageTplEl = dom.querySelector(".image1_tpl_box");

    const profileImage = document.getElementById("profile_image");
    profileImage.innerHTML = "";

    profileImage.appendChild(imageTplEl);
}

/**
 * 파일 삭제 후 후속처리 함수
 *
 * @param seq : 파일 등록 번호
 */
function callbackFileDelete(seq) {
    const fileEl = document.getElementById(`file_${seq}`);   // ``(역따옴표) : 변수를 직접 입력할 수 있도록 함
    fileEl.parentElement.removeChild(fileEl);
}