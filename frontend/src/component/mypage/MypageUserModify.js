import React, {useRef, useState} from 'react';
import Common from "../common/Common";
import logo from "../../src_assets/logo(white).png";
import {BASE_URL, MYPAGE, JOININ} from "../common/config/HostConfig";
import {useNavigate} from "react-router-dom";
import {getToken, getUserIdx, getUserName, getUserNickname
    , getUserBirth, getUserPosition, getUserCareer, getUserProfile} from "../common/util/login-util";
import {BsCheckLg} from "react-icons/bs";
import './scss/MypageModify.scss';



const MypageUserModify = props => {

    const ACCESS_TOKEN = getToken();
    const API_BASE_URL = BASE_URL + MYPAGE;
    const redirection = useNavigate();

    //useRef로 태그 참조하기
    const $fileTag = useRef();

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }
    const [showNickCertificationBtn, setShowNickCertificationBtn] = useState(false); // 닉네임 중복체크

    const USER_NICKNAME = getUserNickname();
    const USER_NAME = getUserName();
    const USER_PROFILE = getUserProfile();
    const USER_POSITION = getUserPosition();
    const USER_CAREER = getUserCareer();
    const USER_BIRTH = getUserBirth();



    // 상태변수로 회원수정 입력값 관리
    const [userValue, setUserValue] = useState({
        userName: USER_NAME,
        userNickName: USER_NICKNAME,
        userBirth: USER_BIRTH,
        userPosition: USER_POSITION,
        userCareer: USER_CAREER,
        userProfile: USER_PROFILE
    });
    // 검증 메세지에 대한 상태변수 관리
    const [message, setMessage] = useState({
        userName: '',
        userNickName: '',
        userBirth: '',
        userPosition: '',
        userCareer: ''
    });

    // 검증 완료 체크에 대한 상태변수 관리
    const [correct, setCorrect] = useState({
        userName: true,
        userNickName: true,
        userBirth: true,
        userPosition: true,
        userCareer: true
    });

    // 검증데이터를 상태변수에 저장하는 함수
    const saveInputState = ({key, inputVal, flag, msg}) => {

        inputVal !== 'pass' && setUserValue({
            ...userValue,
            [key]: inputVal
        });

        setMessage({
            ...message,
            [key]: msg
        });

        setCorrect({
            ...correct,
            [key]: flag
        });
    };

//이미지 파일 상태변수
    const [imgFile, setImgFile] = useState(null);

// 이미지파일을 선택했을 때 썸네일 뿌리기
    const showThumbnailHandler = e => {

        // 첨부된 파일 정보
        const file = $fileTag.current.files[0];

        const reader = new FileReader(); //이미지파일 읽어오기
        reader.readAsDataURL(file);

        reader.onloadend = () => {
            setImgFile(reader.result);
        }
    };

// 이름 입력창 체인지 이벤트 핸들러
    const nameHandler = e => {

        //입력한 값을 상태변수에 저장
        // console.log(e.target.value());

        const nameRegex = /^[가-힣]{2,5}$/;

        const inputVal = e.target.value;
        // 입력값 검증
        let msg; // 검증 메시지를 저장할 변수
        let flag; // 입력 검증체크 변수

        if (!inputVal) {
            msg = '유저 이름은 필수입니다.';
            flag = false;
        } else if (!nameRegex.test(inputVal)) {
            msg = '2~5글자 사이의 한글로 작성하세요!';
            flag = false;
        } else {
            msg = '사용 가능한 이름입니다.';
            flag = true;
        }


        saveInputState({
            key: 'userName',
            inputVal,
            msg,
            flag
        });
    };

// 닉네임 중복체크 서버 통신 함수
    const fetchDuplicateNickCheck = async (e) => {

        const inputNickname = userValue.userNickName;
        console.log(`inputNick : ${inputNickname}`)
        const res = await fetch(`${JOININ}/checknickname?nickname=${inputNickname}`);

        if (res.status === 200) {
            const json = await res.json();
            //true면 중복, false면 사용가능
            if (json){
                alert("이미 존재하는 닉네임입니다.")
                setShowNickCertificationBtn(false);
                let msg = '이미 존재하는 닉네임입니다. 다시 확인해주세요';
                setMessage({...message, userNickName: msg});
                setCorrect({...correct, userNickName: false});
            }else {
                alert("사용 가능한 닉네임입니다.")
                setShowNickCertificationBtn(true);

            }

        } else {
            alert('서버 통신이 원활하지 않습니다!');
        }
    };

// 닉네임 입력창 체인지 이벤트 핸들러
    const nicknameHandler = e => {

        //입력한 값을 상태변수에 저장
        // console.log(e.target.value);

        const nameRegex = /^[가-힣a-zA-Z]{2,8}$/;

        const inputVal = e.target.value;

        // console.log('inputVal의 값 : '+inputVal)
        // 입력값 검증
        let msg; // 검증 메시지를 저장할 변수
        let flag; // 입력 검증체크 변수
        console.log(nameRegex.test(inputVal));
        if (!inputVal) {
            msg = '유저 닉네임은 필수입니다.';
            flag = false;
        } else if (!nameRegex.test(inputVal)) {
            msg = '닉네임은 한글 또는 영어로 2~8자여야 합니다.';
            flag = false;
        } else {
            msg = '사용 가능한 닉네임입니다.';
            flag = true;
        }
        console.log(msg)
        saveInputState({
            key: 'userNickName',
            inputVal,
            msg,
            flag
        });
    };

// userBirth 입력값 변경 핸들러
    const birthHandler = (event) => {
        const inputDate = event.target.value; // 입력받은 문자열
        console.log(inputDate)
        /*
        const year = parseInt(inputDate.substring(0, 4));
        const month = parseInt(inputDate.substring(4, 6));
        const day = parseInt(inputDate.substring(6, 8));

        const localDate = new Date(year, month - 1, day); // JavaScript의 Date 객체 생성
    */


        setUserValue(prevValue => ({
            ...prevValue,
            userBirth: inputDate // Date 객체로 입력받음
        }));

    };

// 포지션 입력받기 핸들러
    const [selectedPosition, setSelectedPosition] = useState('');
    const positionHandler = e => {
        const selectedValue = e.target.value;
        console.log(selectedValue);
        setSelectedPosition(selectedValue);
        setUserValue(prevValue =>({
            ...prevValue,
            userPosition : e.target.value
        }));
    };

//입력칸이 모두 검증에 통과했는지 여부를 검사
    const isValid = () => {
        for (const key in correct) {
            const flag = correct[key];
            if (!flag) return false;
        }
        return true;
    };

    // userCareer 입력값 변경 핸들러
    const careerHandler = (event) => {
        setUserValue(prevValue => ({
            ...prevValue,
            userCareer: parseInt(event.target.value) // 문자열을 정수로 변환
        }));
    };


    // 로그인 상태 검증 핸들러
    const loginCheckHandler = (e) => {
        console.log(`ACCESS_TOKEN = ${ACCESS_TOKEN}`)
        if (ACCESS_TOKEN === '' || ACCESS_TOKEN === null){
            alert('로그인 후 이용가능합니다.')
            e.preventDefault();
            redirection('/login');
        }
    }

    // 회원정보 수정 후 스토리지 정보 갱신
    const updateStorage = (result) => {
        console.log(`updateStorage : ${result.userName}`);
        if (localStorage.getItem('ACCESS_TOKEN') !== null) {
            localStorage.setItem('LOGIN_USER_NAME', result.userName);
            localStorage.setItem('LOGIN_USER_NICKNAME', result.userNickName);
            localStorage.setItem('LOGIN_USER_BIRTH', result.userBirth);
            localStorage.setItem('LOGIN_USER_POSITION', result.userPosition);
            localStorage.setItem('LOGIN_USER_CAREER', result.userCareer);
            localStorage.setItem('LOGIN_USER_PROFILE', result.userProfile);
        }
        sessionStorage.setItem('LOGIN_USER_NAME', result.userName);
        sessionStorage.setItem('LOGIN_USER_NICKNAME', result.userNickName);
        sessionStorage.setItem('LOGIN_USER_BIRTH', result.userBirth);
        sessionStorage.setItem('LOGIN_USER_POSITION', result.userPosition);
        sessionStorage.setItem('LOGIN_USER_CAREER', result.userCareer);
        sessionStorage.setItem('LOGIN_USER_PROFILE', result.userProfile);
    };

    // 회원수정 처리 서버 요청
    const fetchModifyPost = async () => {
        console.log(`fetchSignUpPost의 userValue : ${userValue}`);
        console.log(userValue.userNickName);

        // JSON을 Blob타입으로 변경 후 FormData에 넣기
        const userJsonBlob = new Blob(
            [JSON.stringify(userValue)],
            { type: 'application/json' }
        );

        // 이미지파일과 회원정보 JSON을 하나로 묶어야 함
        const userFormData = new FormData();
        userFormData.append('user', userJsonBlob);
        userFormData.append('profileImage', $fileTag.current.files[0]);

        console.log(`userFormData : `,userFormData)

        const res = await fetch(`${API_BASE_URL}/modify`, {
            method: 'POST',
            headers: headerInfo,
            body: userFormData
        });

        if (res.status === 200) {
            alert('회원 정보 수정에 성공했습니다!');

            // TODO : 회원정보 수정 성공 시, 클라이언트 Storage 도 변경 처리해줘야합니다.
            // 현재 로그인 된 token 으로 변경된 회원 정보만 가져오기
            const res = await fetch(`${API_BASE_URL}/after-modify`, {
                method: 'GET',
                headers: headerInfo
            });
            // const result = await res.text();
            const result = await res.json();
            // console.log(`회원정보수정 성공 후에...result :  ${result}`);
            //
            // console.log(`updateStorage : ${result.userName}`);

            updateStorage(result);
            redirection('/mypage');
        } else {
            alert('서버와의 통신이 원활하지 않습니다.');
        }
    };

//회원수정 버튼 클릭 이벤트 핸들러
    const modifyButtonClickHandler = e => {
        console.log(correct);
        e.preventDefault();  //submit기능 중단 시키기
        // const $nameInput = document.getElementsByName('name');
        console.log(userValue);
        console.log(isValid);
        // 회원수정 서버 요청
        if (isValid()) {
            fetchModifyPost();
            // alert('회원수정 정보를 서버에 전송합니다.')
        } else {
            alert('입력란을 다시 확인해주세요!');
        }
    }

    return (
        <Common className={'modify-wrapper'}>
            <section className={'top-wrapper'}>
                <img src={logo} alt={'logo'} className={'logo'}/>
                <div className={'main-title'}>HI,WE ARE<br/>DDAMDDAM CLUB</div>
            </section>
            <div className={'background'}></div>
            <section className={'form-wrapper'}>
                <div className={"thunmbnail-box"} onClick={() => $fileTag.current.click()}>
                    <img src={imgFile || USER_PROFILE}
                         alt={'profileImg'}
                         className={'profile-img'}
                    />
                </div>
                <label className='signup-img-label' htmlFor='profile-img'>프로필을 등록해주세요</label>
                <input
                    id='profile-img'
                    type='file'
                    style={{display: 'none'}}
                    accept='image/*'
                    ref={$fileTag}
                    onChange={showThumbnailHandler}
                />

                <div className={'input-detail'}>

                    {/*이름*/}
                    <div className={'input-name'}>
                        <input type={"text"} className={'name'} id={'username'} name={'username'}
                               onChange={nameHandler}
                               defaultValue={USER_NAME}/>
                        <span className={correct.userName ? 'correct' : 'not-correct'}>{message.userName}</span>
                        {correct.userName &&
                            <BsCheckLg className={'check'}/>
                        }
                    </div>

                    {/*닉네임*/}
                    <div className={'input-nickname'}>
                        <div className={'nickname-wrapper'}>
                            <input type={"text"} className={'nickname'} id={'nickname'} name={'nickname'}
                                   onChange={nicknameHandler}
                                   defaultValue={USER_NICKNAME}/>
                            <button className={'check-btn'} onClick={fetchDuplicateNickCheck}>
                                <div className={''}>중복확인</div>
                            </button>
                        </div>
                        <span className={correct.userNickName ? 'correct' : 'not-correct'}>{message.userNickName}</span>

                        {correct.userNickName &&
                            <BsCheckLg className={'check'}/>
                        }
                    </div>

                    {/*<input type={"text"} className={'career'} name={'userCareer'} defaultValue={''} id={'userCareer'}*/}
                    {/*       placeholder={'경력 (ex.1년 이상)'} onChange={careerHandler}/>*/}


                    <input type={"date"} className={'birth'} id={'userBirth'} name={'userBirth'}
                           placeholder={'생년월일 8자리 (ex.19960214)'} onChange={birthHandler} defaultValue={USER_BIRTH}/>

                    <select className={'career'} id={'userCareer'} name={'userCareer'} defaultValue={USER_CAREER}
                            onChange={careerHandler}>
                        <option value={'0'}>신입</option>
                        <option value={'1'}>1년 이상</option>
                        <option value={'3'}>3년 이상</option>
                        <option value={'5'}>5년 이상</option>
                    </select>

                    <select className={'position-select'} id={'userPosition'} defaultValue={USER_POSITION}
                            onChange={positionHandler}>
                        <option value={'FRONTEND'}>프론트엔드</option>
                        <option value={'BACKEND'}>백엔드</option>
                    </select>
                </div>

                <button type={'submit'} className={'submit-btn'} onClick={modifyButtonClickHandler}>수정하기</button>
            </section>
        </Common>
    );
};

export default MypageUserModify;
