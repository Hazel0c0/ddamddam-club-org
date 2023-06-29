import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import './scss/QnaMain.scss';
import viewIcon from '../../src_assets/view-icon.png';
import speechBubble from '../../src_assets/speech-bubble.png';
import {QNA} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import {getToken} from "../common/util/login-util";
import {httpStateCatcher} from "../common/util/HttpStateCatcherWrite";

const QnaMain = () => {

    const [topViewQna, setTopViewQna] = useState([]);

    const ACCESS_TOKEN = getToken();
    const redirection = useNavigate();
    const loginCheckHandler = (e) =>{
        // console.log(`ACCESS_TOKEN = ${ACCESS_TOKEN}`)
        if (ACCESS_TOKEN === '' || ACCESS_TOKEN === null){
            alert('로그인 후 이용가능합니다.')
            e.preventDefault();
            redirection('/login');
            // return;
        }
    }

    useEffect(() => {
        fetch(QNA + '/top')
            .then(res => {
                httpStateCatcher(res.status);
                // if (res.status === 500) {
                //     alert('잠시 후 다시 접속해주세요. [서버오류]');
                //     return;
                // }
                return res.json();
            })
            .then(result => {
                if (!!result) {
                    setTopViewQna(result.payload);
                    console.log(result);
                }
            })

    }, [])

    // const handleClick = (boardIdx) => {
    //     // 페이지 전환을 원하는 로직을 작성합니다.
    //     history.push(`/api/ddamddam/qna/${boardIdx}`);
    // };

    return (

        <Common className={'qna-view-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>Q&A</p>
                <p className={'main-sub-title'}>땀땀클럽 회원들과 개발 지식을 공유할 수 있는 공간입니다.</p>
            </div>

            <section className={'top-view-wrapper'}>
                {topViewQna.map((qna, index) => (
                    <div className={'top-section-one'} key={qna.boardIdx}>

                        <h1 className={'top-section-title'}>🔥 주간 조회수 TOP{index + 1} 🔥</h1>
                        <Link to={`/qna/${qna.boardIdx}`} className={'detail-link'} onClick={loginCheckHandler}>
                            <section className={'top-section-wrapper'}>
                                {/*채택완료 수정해야함*/}
                                {/*<div className={'checked'}>값주세요</div>*/}
                                {qna.boardAdoption === 'Y'
                                    ? <div className={'checked'} key={qna.boardAdoption}>
                                        채택완료</div>
                                    : <div className={'not-checked'} key={qna.boardAdoption}>
                                        미채택</div>
                                }
                                <div className={'qna-title'}>{qna.boardTitle}</div>
                                <div className={'qna-content'}>{qna.boardTitle}</div>
                                <div className={'icon-wrapper'}>
                                    <div className={'view-count-wrapper'}>
                                        <img src={viewIcon} alt={'view-count'} className={'view-count-icon'}/>
                                        <span>{qna.boardViewCount}</span>
                                    </div>
                                    <div className={'speech-count-wrapper'}>
                                        <img src={speechBubble} alt={'view-count'} className={'speech-count-icon'}/>
                                        <span>{qna.boardReplyCount}</span>
                                    </div>
                                </div>
                            </section>
                        </Link>
                    </div>
                ))}
            </section>
        </Common>
    );
};

export default QnaMain;
