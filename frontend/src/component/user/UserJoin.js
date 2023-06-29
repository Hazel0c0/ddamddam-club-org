import React, {useEffect, useRef, useState} from 'react';
import Common from "../common/Common";
import logo from "../../src_assets/logo(white).png";
import './scss/UserJoin.scss';
import {BsCheckLg} from "react-icons/bs"
// import ProfileNull from "../../src_assets/ProfileLogo.png"
import {useNavigate, Link} from 'react-router-dom';
import {BASE_URL as BASE, AUTH, JOININ, EMAIL, BASE_URL} from '../../component/common/config/HostConfig';
import useDebounce from "./UseDebounce";

const UserJoin = () => {

    //useRef로 태그 참조하기
    const $fileTag = useRef();

    // 리다이렉트 사용하기
    const redirection = useNavigate();

    // const API_BASE_URL = BASE + AUTH;
    const API_BASE_URL = BASE_URL + JOININ;

    //이메일 주소 선택 값
    const emailValue = useRef();
    //도메인 선택 값
    const [emailDomain, setEmailDomain] = useState('gmail.com');
    const [certification, setCertification] = useState(false);
    const [showCertificationBtn, setShowCertificationBtn] = useState(false);
    const [showCodeCertificationBtn, setShowCodeCertificationBtn] = useState(false);
    const [showNickCertificationBtn, setShowNickCertificationBtn] = useState(false);
    //이메일 인증코드
    const emailCode = useRef();
    const emailCodeCheck = useRef();
    const [emailCodeResult, setEmailCodeResult] = useState(false);

    //이미지 업로드
    const [imgData, setImageData] = useState([]);

    // 상태변수로 회원가입 입력값 관리
    const [userValue, setUserValue] = useState({
        userEmail: '',
        userPw: '',
        userName: '',
        userNickName: '',
        userBirth: '',
        userPosition: 'FRONTEND',
        userCareer: '',
    });

    // 검증 메세지에 대한 상태변수 관리
    const [message, setMessage] = useState({
        userEmail: '',
        userPw: '',
        passwordCheck: '',
        userName: '',
        userNickName: '',
        userBirth: '',
        userPosition: '',
        userCareer: ''
    });

    // 검증 완료 체크에 대한 상태변수 관리
    const [correct, setCorrect] = useState({
        userEmail: false,
        userCode: false,
        userPw: false,
        passwordCheck: false,
        userName: false,
        userNickName: false,
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


    // 이름 입력창 체인지 이벤트 핸들러
    const nameHandler = e => {

        //입력한 값을 상태변수에 저장

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
        //입력한 값을 상태변수에 저장
        // console.log(e.target.value);

        const nameRegex = /^[가-힣a-zA-Z]{2,8}$/;

        const inputVal = document.getElementById('nickname').value;
      
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
        }

        
        saveInputState({
            key: 'userNickName',
            inputVal,
            msg,
            flag
        });
        if(nameRegex.test(inputVal)){
            console.log(`inputNick : ${inputVal}`)
        const res = await fetch(`${API_BASE_URL}/checknickname?nickname=${inputVal}`);

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
                setCorrect({...correct, userNickName: true});
            }
        } else {
            alert('서버 통신이 원활하지 않습니다!');
        }
        // const inputNickname = userValue.userNickName;
        
    }

    };

    // 이메일 중복체크 서버 통신 함수
    const fetchDuplicateCheck = async (e) => {

        const inputEmail = userValue.userEmail;
        console.log(`inputEmail : ${inputEmail}`)
        const res = await fetch(`${API_BASE_URL}/check?email=${inputEmail}`);

        let msg = '', flag = false;
        if (res.status === 200) {
            const json = await res.json();
            console.log(json);
            if (json) {
                alert("이메일이 중복되었습니다");
                return;
            } else {
                flag = true;
                alert("사용 가능한 이메일입니다.");
                setShowCertificationBtn(true);
            }
        } else {
            alert('서버 통신이 원활하지 않습니다!');
        }
        setShowCodeCertificationBtn(true)
        setUserValue({...userValue, userEmail: inputEmail});
        setMessage({...message, userEmail: msg});
        setCorrect({...correct, userEmail: flag});
    };


    //인증하기 클릭
    const certificationHandler = async () => {
        alert("해당 이메일로 코드를 보냈습니다. 확인 후 인증코드를 입력해주세요.")
        setCertification(true);

        const inputEmail = userValue.userEmail;
        console.log(`인증하기 메일 보내기 inputEmail주소 : ${inputEmail}`)
        const res = fetch(`${EMAIL}/send`, {
            method: 'POST',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify({
                email: inputEmail
            })
        })
        setShowCodeCertificationBtn(false);
        console.log(res)
    }

    //코드 입력 완료
    const submitCodeHander = async () => {
        const code = emailCode.current.value;
        console.log(`입력 code value : ${code}`);

        const res = await fetch(`${EMAIL}/check`, {
            method: 'POST',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify({
                code: code
            })
        })

        const result = await res.json();

        console.log(`result = ${JSON.stringify(result)}`);
        console.log(`result.checkResult = ${result.checkResult}`);
        // console.log(JSON.stringify(result))
        // console.log(`코드 입력 후 result : ${result}`)

        if (result.checkResult) {
            alert("인증이 완료되었습니다.");
            setEmailCodeResult(true);
            setCorrect(prevState => ({
                ...prevState,
                userCode: true
            }));
            // emailCodeCheck.current.textContent = '제출완료';
        } else {
            alert("인증에 실패하였습니다. 다시 확인해주세요.");
            setEmailCodeResult(false);
        }
    }

    // 이메일 입력창 체인지 이벤트 핸들러
    const emailHandler = e => {

        //이메일 입력
        const inputEmail = e.target.value;

        console.log(`emailDomain : `, emailDomain)
        const inputVal = `${inputEmail}@${emailDomain}`;
        console.log(`emailResult Value : ${inputVal}`);
        // const emailRegex = /^[a-z0-9\.\-_]+@([a-z0-9\-]+\.)+[a-z]{2,6}$/;
        const emailRegex = /^[a-z0-9\.\-_]+/;

        let msg = '', flag = true;

        saveInputState({
            key: 'userEmail',
            inputVal,
            msg,
            flag
        });

    };

    //이메일 도메인 선택
    const handleEmailChange = (e) => {
        const emailDomainValue = e.target.value;
        console.log(`emailDomainValue의 값 : `, emailDomainValue);
        setEmailDomain(emailDomainValue);

        //이메일 입력
        const inputEmail = document.querySelector('.email-input').value;console.log(`emailDomain : `, emailDomain)
        const inputVal = `${inputEmail}@${emailDomainValue}`;
        console.log(`emailResult Value : ${inputVal}`);

        let msg = '', flag = true;

        saveInputState({
            key: 'userEmail',
            inputVal,
            msg,
            flag
        });
    }


// 패스워드 입력창 체인지 이벤트 핸들러
    const passwordHandler = e => {

        // 패스워드가 변동되면 확인란을 비우기
        document.getElementById('passwordCheck').value = '';
        // document.getElementById('check-span').textContent = '';

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
            key: 'userPw',
            inputVal,
            msg,
            flag
        });

    };

    // 비밀번호 확인란 검증 이벤트 핸들러
    const pwcheckHandler = e => {
        // 검증 시작
        let msg, flag;
        if (!e.target.value) { // 패스워드 안적은거
            msg = '비밀번호 확인란은 필수값입니다!';
            flag = false;
        } else if (userValue.userPw !== e.target.value) {
            msg = '패스워드가 일치하지 않습니다.';
            flag = false;
        } else {
            msg = '패스워드가 일치합니다.';
            flag = true;
        }

        saveInputState({
            key: 'passwordCheck',
            inputVal: 'pass',
            msg,
            flag
        });

    };


// userBirth 입력값 변경 핸들러
    const birthHandler = (event) => {
        const inputDate = event.target.value; // 입력받은 문자열
        console.log(inputDate)

        setUserValue(prevValue => ({
            ...prevValue,
            userBirth: inputDate // Date 객체로 입력받음
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
        console.log(`fetchSignUpPost의 userValue : ${userValue}`);


        // JSON을 Blob타입으로 변경 후 FormData에 넣기
        const userJsonBlob = new Blob(
          [JSON.stringify(userValue)],
          { type: 'application/json' }
        );

        // 이미지파일과 회원정보 JSON을 하나로 묶어야 함
        const userFormData = new FormData();
        userFormData.append('user', userJsonBlob);
        userFormData.append('profileImage', $fileTag.current.files[0]);
        // userFormData.append('profileImage', $fileTag.current.files[0],{ type: `image/jpeg`});

        console.log(`userFormData : `,userFormData)

        const res = await fetch(`${API_BASE_URL}/signup`, {
            method: 'POST',
            body: userFormData
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
        console.log(correct);
        e.preventDefault();  //submnsole.log(userValue)
        console.log(isValid)
        // console.log(`imgFile의 값 : `,imgFile)
        // 회원가입 서버 요청
        if (isValid()) {
            fetchSignUpPost();
            alert('회원가입 정보를 서버에 전송합니다.')
        } else {
            alert('입력란을 다시 확인해주세요!');
        }
    }
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


    //렌더링이 끝난 이후 실행되는 함수
    useEffect(() => {
        console.log(emailDomain)
    }, [emailValue, imgFile, emailDomain]);

    return (
        <Common className={'join-wrapper'}>
            <section className={'top-wrapper'}>
                <img src={logo} alt={'logo'} className={'logo'}/>
                <div className={'main-title'}>HI,WE ARE<br/>DDAMDDAM CLUB</div>
            </section>
            <div className={'background'}></div>
            <section className={'form-wrapper'}>
                <div className={"thunmbnail-box"} onClick={() => $fileTag.current.click()}>
                    <img src={imgFile || require('../../src_assets/ProfileLogo.png')}
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
                <div className={'input-email'}>
                    <input type={"text"} className={'email-input'} id={'userEmail'} name={'userEmail'}
                           placeholder={'이메일'} onChange={emailHandler}/>
                    <select className={'email-select'} defaultValue={'gmail.com'} ref={emailValue}
                            onChange={handleEmailChange}>
                        <option value={'gmail.com'}>@gmail.com</option>
                        <option value={'naver.com'}>@naver.com</option>
                        {/*<option value={'daum.net'}>@daum.net</option>*/}
                    </select>

                    {showCertificationBtn
                        ?
                        <button className={'endCheck-btn'} disabled={true}>
                            <div className={'endCheck-email'}>중복완료</div>
                        </button>
                        :
                        <button className={'check-btn'} onClick={fetchDuplicateCheck}>
                            <div className={''}>중복확인</div>
                        </button>
                    }

                </div>


                <section className={'email-certification-wrapper'}>
                    {showCodeCertificationBtn &&
                        <>
                            <button className={'email-check-btn'} onClick={certificationHandler}
                                    disabled={emailCodeResult} ref={emailCodeCheck}>인증하기
                            </button>
                        </>
                    }
                    {/* 인증하기 버튼 누르면 나오게 */}
                    {certification &&
                        <section className={"check-email-wrapper"}>
                            <div>
                                <input type={"text"} className={"check-email-input"} name={"code"} placeholder={""}
                                       ref={emailCode}/>

                                {emailCodeResult ?
                                    <button className={"confirm-check-email"} disabled={emailCodeResult}>인증완료</button>
                                    : <button className={"confirm-check-email"} onClick={submitCodeHander}>인증하기</button>
                                }

                            </div>
                        </section>
                    }
                </section>

                <div className={'input-pw'}>
                    <input type={"password"} className={'pw'} id={'userPw'} name={'userPw'} placeholder={'비밀번호'}
                           onChange={passwordHandler}/>
                    <span className={correct.userPw ? 'correct' : 'not-correct'}>{message.userPw}</span>
                    {correct.userPw &&
                        <BsCheckLg className={'check'}/>
                    }
                </div>
                <div className={'input-pwcheck'}>
                    <input type={"password"} className={'pw-check'} id={'passwordCheck'} name={'pw-check'}
                           placeholder={'비밀번호 확인'} onChange={pwcheckHandler}/>

                    <span className={correct.passwordCheck ? 'correct' : 'not-correct'}>{message.passwordCheck}</span>

                    {correct.passwordCheck &&
                        <BsCheckLg className={'check'}/>
                    }
                </div>


                <div className={'input-detail'}>

                    {/*이름*/}
                    <div className={'input-name'}>
                        <input type={"text"} className={'name'} id={'username'} name={'username'} placeholder={'이름'}
                               onChange={nameHandler}/>
                        <span className={correct.userName ? 'correct' : 'not-correct'}>{message.userName}</span>
                        {correct.userName &&
                            <BsCheckLg className={'check'}/>
                        }
                    </div>

                    {/*닉네임*/}
                    <div className={'input-nickname'}>
                        <div className={'nickname-wrapper'}>
                            <input type={"text"} className={'nickname'} id={'nickname'} name={'nickname'}
                                   placeholder={'닉네임'}
                                //    onChange={nicknameHandler}
                                   />

                            {/*변경하기*/}
                            {showNickCertificationBtn
                                ?
                                <button className={'endCheck-btn'} disabled={true}>
                                    <div className={'endCheck-email'}>중복완료</div>
                                </button>
                                :
                                <button className={'check-btn'} onClick={fetchDuplicateNickCheck}>
                                    <div className={''}>중복확인</div>
                                </button>
                            }
                        </div>
                        <span className={correct.userNickName ? 'correct' : 'not-correct'}>{message.userNickName}</span>

                        {correct.userNickName &&
                            <BsCheckLg className={'check'}/>
                        }
                    </div>

                    {/*<input type={"text"} className={'career'} name={'userCareer'} defaultValue={''} id={'userCareer'}*/}
                    {/*       placeholder={'경력 (ex.1년 이상)'} onChange={careerHandler}/>*/}


                    <input type={"date"} className={'birth'} id={'userBirth'} name={'userBirth'}
                           placeholder={'생년월일 8자리 (ex.19960214)'} onChange={birthHandler}/>

                    <select className={'career'} id={'userCareer'} name={'userCareer'} defaultValue={'0'}
                            onChange={careerHandler}>
                        <option value={'0'}>신입</option>
                        <option value={'1'}>1년 이상</option>
                        <option value={'3'}>3년 이상</option>
                        <option value={'5'}>5년 이상</option>
                    </select>

                    <select className={'position-select'} id={'userPosition'} defaultValue={'selectedPosition'}
                            onChange={positionHandler}>
                        <option value={'FRONTEND'}>프론트엔드</option>
                        <option value={'BACKEND'}>백엔드</option>
                    </select>
                </div>

                <button type={'submit'} className={'submit-btn'} onClick={joinButtonClickHandler}>가입완료</button>
            </section>
        </Common>
    );
};

export default UserJoin;