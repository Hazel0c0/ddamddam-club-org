import React, {useCallback, useEffect, useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/QnaWrite.scss';
import {MENTOR, QNA} from "../common/config/HostConfig";
import {Link, useNavigate, useParams} from "react-router-dom";
import Tags from '@yaireo/tagify/dist/react.tagify';
import {getWhitelistFromServer, getValue} from './hashTagConfig/mockServer'

const MentorsWrite = () => {
    const redirection = useNavigate();
    const {boardIdx} = useParams();
    const [detailQna, setDetailQna] = useState([]);
    const [hashTag, setHashTag] = useState("");
    const [textInput, setTextInput] = useState(
        {
            boardTitle: '',
            boardContent: '',
            hashtagList: []
        }
    )
    const tagifyRef1 = useRef();
    const [tagifySettings, setTagifySettings] = useState([]);
    const [tagifyProps, setTagifyProps] = useState({});

    useEffect(() => {
        console.log(boardIdx);
        fetch(`//localhost:8181/api/ddamddam/qna/${boardIdx}`)
            .then((res) => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then((result) => {
                setDetailQna(result.payload);

                console.log(`수정할 value의 값: ${JSON.stringify(result.payload)}`);
                // const hashTagList = result.payload.hashtagList;
                // let hashTagToString = hashTagList.join(',');
                // setHashTag(hashTagToString);


                // const hashTagList = result.payload.hashtagList;

                // let hashTagToString = ""
                // for (let i = 0; i < hashTagList.length; i++) {
                //     hashTagToString += hashTagList[i];
                //     if (i !== hashTagList.length - 1) {
                //         hashTagToString += ",";
                //     }
                // }
                // console.log(hashTagToString);
                // setHashTag(hashTagToString);
            });


        setTagifyProps({loading: false});

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
        console.log(textInput);
        const {boardTitle, boardContent, hashtagList} = textInput;
        const res = fetch(`${QNA}/modify/${boardIdx}`, {
            method: 'PATCH',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify({
                boardIdx: boardIdx,
                boardTitle: boardTitle,
                boardContent: boardContent,
                hashtagList: hashtagList
            })
        });

        const result = await res;
        if (result.status === 200){
            alert("게시글 수정이 완료되었습니다.");
            redirection(`/api/ddamddam/qna/${boardIdx}`)

        }
        console.log(result)
        console.log(JSON.stringify(result));

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
        console.log("CHANGED:" + hashTagArr);

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
                        // defaultValue="a,b,c"
                        defaultValue={hashTag}
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