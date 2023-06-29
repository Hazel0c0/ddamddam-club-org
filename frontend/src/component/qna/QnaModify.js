import React, {useCallback, useEffect, useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/QnaWrite.scss';
import {MENTOR, QNA} from "../common/config/HostConfig";
import {Link, useNavigate, useParams} from "react-router-dom";
import Tags from '@yaireo/tagify/dist/react.tagify';
import {getWhitelistFromServer, getValue} from './hashTagConfig/mockServer'
import {getToken} from "../common/util/login-util";
import header from "../common/Header";
import {httpStateCatcher, httpStateCatcherModify} from "../common/util/HttpStateCatcherWrite";

const MentorsWrite = () => {
    const {boardIdx} = useParams();
    const [detailQna, setDetailQna] = useState([]);
    const [textInput, setTextInput] = useState(
        {
            boardTitle: '',
            boardContent: '',
            hashtagList: []
        }
    )
    // const [hashTagValue, setHashTagValue] =useState([]);
    const tagifyRef1 = useRef();
    const [tagifySettings, setTagifySettings] = useState([]);
    const [tagifyProps, setTagifyProps] = useState({});

    const ACCESS_TOKEN = getToken();
    const requestHeader = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    };

    const modifiedBoard = async () => {

        const res = await fetch(`${QNA}/${boardIdx}`, {
            method: 'GET',
            headers: requestHeader,
        })

        httpStateCatcher(res.status);
        // if (res.status === 500) {
        //     alert('잠시 후 다시 접속해주세요.[서버오류]');
        //     return;
        // }

        const result = await res.json();
        setDetailQna(result.payload);
        setTextInput({
            boardTitle: result.payload.boardTitle,
            boardContent: result.payload.boardContent,
            hashtagList: result.payload.hashtagList
        })
        // console.log(`수정할 value의 값 : `, result.payload);
    }


    useEffect(() => {

        modifiedBoard();

        // setTagifyProps({loading: false});

        // simulate state change where some tags were deleted
        // const timer = setTimeout(
        //     () =>
        //         setTagifyProps((lastProps) => ({
        //             ...lastProps,
        //             // defaultValue: ["wef"]
        //             // showFilteredDropdown: false
        //         })),
        //     5000
        // );
        //
        // return () => {
        //     clearTimeout(timer);
        // };

    }, [])


    const hashTagStr = textInput.hashtagList.reduce((curVal, hashTag) => {
        return curVal + hashTag;
    }, '');
    // console.log('hashStr: ', hashTagStr);


    const handleSelect = (e) => {
        const {name, value} = e.target;
        let parseValue = value;

        setTextInput((prevTextInput) => ({
            ...prevTextInput,
            [name]: parseValue
        }));
    };

    //수정완료버튼
    const modifySubmitHandler = async () => {
        // console.log(textInput);
        const {boardTitle, boardContent, hashtagList} = textInput;
        const res = fetch(`${QNA}/modify/${boardIdx}`, {
            method: 'PATCH',
            headers: requestHeader,
            body: JSON.stringify({
                boardIdx: boardIdx,
                boardTitle: boardTitle,
                boardContent: boardContent,
                hashtagList: hashtagList
            })
        });

        const result = await res;
        httpStateCatcherModify(result.status);
        if (result.status === 200) {
            alert("게시글 수정이 완료되었습니다.");
            window.location.href = `/qna/${boardIdx}`
        }
        // console.log(result)
        // console.log(JSON.stringify(result));

    }

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

    const settings = {
        ...baseTagifySettings,
        ...tagifySettings
    }

    const onChange = useCallback(e => {

        const inputHashTag = e.detail.tagify.getCleanValue();
        const hashTagArr = inputHashTag.map(hashtag => (hashtag.value));
        // console.log("CHANGED:" + hashTagArr);

        setTextInput((prevTextInput) => ({
            ...prevTextInput,
            hashtagList: hashTagArr
        }));
    }, [])

    const test = [0,1,2]

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
                        defaultValue={detailQna.boardTitle}
                        // value={''}
                        onChange={handleSelect}
                    />
                </div>
                <div className={'hashtag-wrapper'}>
                    <h1 className={'sub-title'}>해쉬태그</h1>
                    <Tags
                        tagifyRef={tagifyRef1}
                        settings={settings}
                        // defaultValue={hashTagStr}
                        value = {textInput.hashtagList}
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
                    // value={textInput.mentorContent}
                          defaultValue={detailQna.boardContent}
                    // value={''}
                          name="boardContent"
                          onChange={handleSelect}
                />
            </section>

            <div className={'btn-wrapper'}>
                <Link to={'/qna'} className={'close-btn-a'}>
                    <button className={'close-btn'}>취소하기</button>
                </Link>
                {/*<button className={'submit-btn'} onClick={handleSubmit}>작성완료</button>*/}
                <button className={'submit-btn'} onClick={modifySubmitHandler}>수정완료</button>
            </div>
        </Common>
    );
};

export default MentorsWrite;