import React, {userEffect, useState} from 'react';
import Common from "../common/Common";
import logo from "../../src_assets/logo(white).png";
import profile from "../../src_assets/IMG_4525.JPG";
import './scss/UserJoin.scss';
// 리다이렉트 사용하기
import { useNavigate, Link } from 'react-router-dom';
import { API_BASE_URL as BASE, USER } from '../../config/host-config';

const UserJoin = () => {

    // 리다이렉트 사용하기
    const redirection = useNavigate();

    const API_BASE_URL = BASE + USER;

    // 상태변수로 회원가입 입력값 관리
    const [userValue, setUserValue] = useState({
        userEmail:'',
        password: '',
        userName: '',
        nickName: '',
        userBirth:'',
        userPosition: '',
        userCareer: '',
        userProfile:''
    });

    // 검증 메세지에 대한 상태변수 관리
    const [message, setMessage] = useState({
        userEmail:'',
        password: '',
        userName: '',
        nickName: '',
        userBirth:'',
        userPosition: '',
        userCareer: ''
    });

    // 검증 완료 체크에 대한 상태변수 관리
    const [correct, setCorrect] = useState({
        userEmail: false,
        password: false,
        userName: false,
        nickName: false,
        userBirth: false,
        userPosition: false,
        userCareer: false
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

    // 닉네임 입력창 체인지 이벤트 핸들러
    const nicknameHandler = e => {

        //입력한 값을 상태변수에 저장
        // console.log(e.target.value());

        const nameRegex = /^[가-힣a-zA-Z]{2,5}$/;

        const inputVal = e.target.value;
        // 입력값 검증
        let msg; // 검증 메시지를 저장할 변수
        let flag; // 입력 검증체크 변수

        if (!inputVal) {
            msg = '유저 닉네임은 필수입니다.';
            flag = false;
        } else if (!nameRegex.test(inputVal)) {
            msg = '닉네임은 한글 또는 영어로 2~5자여야 합니다.';
            flag = false;
        } else {
            msg = '사용 가능한 닉네임입니다.';
            flag = true;
        }

        saveInputState({
            key: 'nickname',
            inputVal,
            msg,
            flag
        });
    };

    // 이메일 중복체크 서버 통신 함수
    const fetchDuplicateCheck = async (email) => {

        const res = await fetch(`${API_BASE_URL}/check?email=${email}`);

        let msg = '', flag = false;
        if (res.status === 200) {
            const json = await res.json();
            console.log(json);
            if (json) {
                msg = '이메일이 중복되었습니다!';
                flag = false;
            } else {
                msg = '사용 가능한 이메일입니다.';
                flag = true;
            }
        } else {
            alert('서버 통신이 원활하지 않습니다!');
        }

        setUserValue({...userValue, userEmail: email });
        setMessage({...message, userEmail: msg });
        setCorrect({...correct, userEmail: flag });

    };

    // 이메일 입력창 체인지 이벤트 핸들러
    const emailHandler = e => {

        const inputVal = e.target.value;

        const emailRegex = /^[a-z0-9\.\-_]+@([a-z0-9\-]+\.)+[a-z]{2,6}$/;

        let msg, flag;
        if (!inputVal) {
            msg = '이메일은 필수값입니다!';
            flag = false;
        } else if (!emailRegex.test(inputVal)) {
            msg = '이메일 형식이 아닙니다!';
            flag = false;
        } else {
            // 이메일 중복체크
            fetchDuplicateCheck(inputVal);
            return;
        }

        saveInputState({
            key: 'userEmail',
            inputVal,
            msg,
            flag
        });

    };



// 패스워드 입력창 체인지 이벤트 핸들러
    const passwordHandler = e => {

        // 패스워드가 변동되면 확인란을 비우기
        document.getElementById('password-check').value = '';
        document.getElementById('check-span').textContent = '';

        setMessage({...message, passwordCheck: ''});
        setCorrect({...correct, passwordCheck: false});

        const inputVal = e.target.value;

        const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/;

        // 검증 시작
        let msg, flag;
        if (!e.target.value) { // 패스워드 안적었을때
            msg = '비밀번호는 필수값입니다!';
            flag = false;
        } else if (!pwRegex.test(e.target.value)) {
            msg = '8글자 이상의 영문,숫자,특수문자를 포함해주세요!';
            flag = false;
        } else {
            msg = '사용 가능한 비밀번호입니다.';
            flag = true;
        }

        saveInputState({
            key: 'password',
            inputVal,
            msg,
            flag
        });

    };





// userBirth 입력값 변경 핸들러
    const birthHandler = (event) => {
        setUserValue(prevValue => ({
            ...prevValue,
            userBirth: event.target.value // 문자열로 입력받음
        }));
    };

    // userCareer 입력값 변경 핸들러
    const careerHandler = (event) => {
        setUserValue(prevValue => ({
            ...prevValue,
            userCareer: parseInt(event.target.value) // 문자열을 정수로 변환
        }));
    };

// 포지션 입력받기 핸들러
    const [selectedPosition, setSelectedPosition] = useState('');
    const positionHandler = e => {
        const selectedValue = e.target.value;
        setSelectedPosition(selectedValue);
    };

    //입력칸이 모두 검증에 통과했는지 여부를 검사
    const isValid = () => {

        for (const key in correct) {
            const flag = correct[key];
            if (!flag) return false;
        }
        return true;
    };

    // 회원가입 처리 서버 요청
    const fetchSignUpPost = async () => {

        const res = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: { 'content-type' : 'application/json' },
            body: JSON.stringify(userValue)
        });

        if (res.status === 200) {
            alert('회원가입에 성공했습니다! 축하합니다!');
            // 로그인 페이지로 리다이렉트
            // window.location.href = '/login';
            redirection('/login');
        } else {
            alert('서버와의 통신이 원활하지 않습니다.');
        }
    };


    //회원가입 버튼 클릭 이벤트 핸들러
    const joinButtonClickHandler = e => {

        e.preventDefault();  //submit기능 중단 시키기
        // const $nameInput = document.getElementsByName('name');

        // 회원가입 서버 요청
        if (isValid()) {
            fetchSignUpPost();
            // alert('회원가입 정보를 서버에 전송합니다.')
        } else {
            alert('입력란을 다시 확인해주세요!');
        }
    }


    //렌더링이 끝난 이후 실행되는 함수
    userEffect(() => {
    }, []);
    return (
        <Common className={'join-wrapper'}>
            <section className={'top-wrapper'}>
                <img src={logo} alt={'logo'} className={'logo'}/>
                <div className={'main-title'}>HI,WE ARE<br/>DDAMDDAM CLUB</div>
            </section>
            <div className={'background'}></div>
            <section className={'form-wrapper'}>
                <img src={profile} alt={'profileImg'} className={'profile-img'}></img>
                <div className={'profile-img-text'}>프로필을 등록해주세요</div>
                <div className={'input-id'}>
                    <input type={"text"} className={'id'} name={'id'} placeholder={'아이디'}  />
                    <button className={'check-btn'}>중복확인</button>
                </div>

                <div className={'input-pw'}>
                    <input type={"text"} className={'pw'} name={'pw'} placeholder={'비밀번호'} onChange={passwordHandler}/>
                    <span style={
                        correct.password ? {color: 'green'} :{color : 'red'} //입력값검증시에 글씨 색깔
                    }>{message.password}</span>
                </div>

                <div className={'input-email'}>
                    <input type={"text"} className={'email-input'} name={'email'} placeholder={'이메일'} onChange={emailHandler}/>
                        <select className={'email-select'} value={''} >
                            <option value={'gmail.com'}>@gmail.com</option>
                            <option value={'gmail.com'}>@gmail.com</option>
                            <option value={'gmail.com'}>@gmail.com</option>
                        </select>
                    <span style={
                        correct.userEmail ? {color: 'green'} :{color : 'red'} //입력값검증시에 글씨 색깔
                    }>{message.userEmail}</span>
                    <button className={'check-btn'}>인증하기</button>
                </div>

                <div className={'input-nickname'}>
                    <input type={"text"} className={'nickname'} name={'nickname'} placeholder={'닉네임'} onChange={nicknameHandler}/>
                    <span style={
                        correct.nickName ? {color: 'green'} :{color : 'red'} //입력값검증시에 글씨 색깔
                    }>{message.nickName}</span>
                </div>

                <div className={'input-detail'}>
                    <input type={"text"} className={'name'} id={'username'} name={'name'} placeholder={'이름'} onChange={nameHandler}/>
                    <span style={
                        correct.userName ? {color: 'green'} :{color : 'red'} //입력값검증시 글씨 색깔 빨강색
                    }>{message.userName}</span>
                    <input type={"text"} className={'career'} name={'career'} placeholder={'경력'} onChange={careerHandler}/>
                    <input type={"text"} className={'birth'} name={'birth'} placeholder={'생년월일 8자리'} onChange={birthHandler}/>
                    <select className={'position-select'} value={'selectedPosition'} onChange={positionHandler}>
                        <option value={'백엔드'}>백엔드</option>
                        <option value={'프론트엔드'}>프론트엔드</option>
                    </select>
                </div>

                <button type={'submit'} className={'submit-btn'} onClick={joinButtonClickHandler}>가입완료</button>
            </section>
        </Common>
    );
};

export default UserJoin;