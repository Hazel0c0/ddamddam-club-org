import React, {useCallback, useEffect, useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/QnaWrite.scss';
import {MENTOR, QNA} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import Tags from '@yaireo/tagify/dist/react.tagify';
import {getWhitelistFromServer, getValue} from './hashTagConfig/mockServer'

const MentorsWrite = () => {
    const redirection = useNavigate();
    const [textInput, setTextInput] = useState(
        {
            boardTitle: '',
            boardContent: '',
            hashtagList: []
        }
    )

    const handleSelect = (e) => {
        const {name, value} = e.target;
        let parseValue = value;

        setTextInput((prevTextInput) => ({
            ...prevTextInput,
            [name]: parseValue
        }));
    };

    const submitTest =() =>{
        console.log(textInput)
    }

    const handleSubmit = async () => {
        console.log(textInput);
        // 수집한 값들을 이용하여 비동기 POST 요청 수행
        const {
            boardTitle,
            boardContent,
            hashtagList
        } = textInput;

        if (boardTitle.length === 0 || boardContent.length === 0 || hashtagList.length === 0) {
            alert('공백 없이 입력해주세요.');
        } else {
            const data = {
                boardTitle: boardTitle,
                boardContent: boardContent,
                hashtagList: hashtagList
            };
            // 비동기 POST 요청 처리 로직 작성
            console.log(data); // 확인을 위해 콘솔에 출력

            const res = await fetch(QNA + '/write', {
                method: 'POST',
                headers: {'content-type': 'application/json'},
                body: JSON.stringify(data)
            });

            if (res.status === 400) {
                const text = await res;
                console.log(`오류시 알람 : ${text}`);
                return;
            } else {
                alert('작성이 완료되었습니다.')
                redirection('/qna')
            }
        }
        ;
    };

    //해쉬태그 핸들러
    const baseTagifySettings = {
        // blacklist: ["xxx", "yyy", "zzz"],
        maxTags: 10,
        //backspace: "edit",
        placeholder: "해쉬태그를 입력해주세요.",
        dropdown: {
            enabled: 0 // a;ways show suggestions dropdown
        }
    }

    const tagifyRef1 = useRef();
    const [tagifySettings, setTagifySettings] = useState([]);
    const [tagifyProps, setTagifyProps] = useState({});

    useEffect(() => {
        setTagifyProps({loading: false})

        getWhitelistFromServer(2000).then((response) => {
            setTagifyProps((lastProps) => ({
                ...lastProps,
                // whitelist: response,
                // showFilteredDropdown: "a",
                loading: false
            }))
        })

        // simulate setting tags value via server request
        getValue(3000).then((response) =>
            setTagifyProps((lastProps) => ({...lastProps, defaultValue: response}))
        )

        // simulate state change where some tags were deleted
        setTimeout(
            () =>
                setTagifyProps((lastProps) => ({
                    ...lastProps,
                    defaultValue: [""]
                    // showFilteredDropdown: false
                })),
            5000
        )


    }, [])

    const settings = {
        ...baseTagifySettings,
        ...tagifySettings
    }

    const onChange = useCallback(e => {

        const inputHashTag = e.detail.tagify.getCleanValue();
        const hashTagArr = inputHashTag.map(hashtag=> (hashtag.value));
        console.log("CHANGED:"+hashTagArr);

        setTextInput((prevTextInput) => ({
            ...prevTextInput,
            hashtagList: hashTagArr
        }));
    }, [])

    return (
        <Common className={'qna-write-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>Q&A</p>
                <p className={'main-sub-title'}>땀땀클럽 회원들과 개발 지식을 공유할 수 있는 공간입니다.</p>
            </div>

            <section className={'write-form-wrapper'}>
                <div className={'title-input-wrapper'}>
                    <h1 className={'sub-title'}>제목</h1>
                    <input
                        type={"text"}
                        placeholder={'제목을 입력하세요'}
                        className={'title-text-input'}
                        name={'boardTitle'}
                        value={textInput.boardTitle}
                        onChange={handleSelect}
                    />
                </div>
                <div className={'hashtag-wrapper'}>
                    <h1 className={'sub-title'}>해쉬태그</h1>
                    <Tags
                        tagifyRef={tagifyRef1}
                        settings={settings}
                        // defaultValue="a,b,c"
                        autoFocus={true}
                        {...tagifyProps}
                        onChange={onChange}
                    />
                </div>
            </section>

            <section>
                <textarea type="text"
                          placeholder={"내용을 입력해주세요"}
                          className={'content'}
                          value={textInput.mentorContent}
                          name="boardContent"
                          onChange={handleSelect}
                />
            </section>

            <div className={'btn-wrapper'}>
                <Link to={'/qna'} className={'close-btn-a'}>
                    <button className={'close-btn'}>취소하기</button>
                </Link>
                {/*<button className={'submit-btn'} onClick={handleSubmit}>작성완료</button>*/}
                <button className={'submit-btn'} onClick={handleSubmit}>작성완료</button>
            </div>
        </Common>
    );
};

export default MentorsWrite;