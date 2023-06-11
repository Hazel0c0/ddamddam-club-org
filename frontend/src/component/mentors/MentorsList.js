import React, {useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import Common from "../common/Common";
import Modal from 'react-bootstrap/Modal';
import {TfiClose} from 'react-icons/tfi';
import './scss/MentorsList.scss';


const MentorsList = () => {

    const [mentorsList, setMentorsList] = useState([]);
    const [pageNation, setPageNation] = useState([]);

    // 모달 useState
    const [detailMember, setDetailMember] = useState([]);
    const [show, setShow] = useState(false);

    const handleClose = () => {
        setShow(false)

    };
    const handleShow = (e) => {
        setShow(true)
        const detailIdx= e.target.closest('.mentors-list').querySelector('.member-idx').value

        fetch('http://localhost:8181/api/ddamddam/mentors/detail?mentorIdx='+detailIdx)
            .then(res =>{
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then(result => {
                if (!!result) {
                    setDetailMember(result);
                }
                console.log(result)
                console.log(`detailMember 결과 : ${detailMember}`)
            });

    };

    const logTest = detailMember;

    // fetch('http://localhost:8181/api/ddamddam/mentors/list?page=&size=&sort=')
    useEffect(() => {
        fetch('http://localhost:8181/api/ddamddam/mentors/list?page=1&size=9&sort=mentorDate')
            .then(res => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then(result => {
                if (!!result) {
                    setMentorsList(result.mentors);
                    setPageNation(result.pageInfo);
                }

            });
    }, []);

    const test = (e) =>{
        console.log(e.target);
    }
    // http://localhost:8181/api/ddamddam/mentors/detail?mentorIdx=1
    return (
        <Common className={'mentors-list-wrapper'}>
            {mentorsList.map((mentor) => (
                <div className={'mentors-list'} key={mentor.idx} onClick={handleShow}>
                    <input type={'hidden'} value={mentor.idx} className={'member-idx'}/>
                    {/*<Link to={`/mentors/detail/mentorIdx/${mentor.idx}`}>*/}
                    <div className={'speech-bubble'} key={mentor.title}>
                        {mentor.title}
                    </div>
                    {/*Profile 수정 필요*/}
                    <div className={'profile-img'}></div>

                    <div className={'list-text-wrapper'}>
                        <div className={'writer'} key={mentor.nickName}>
                            {mentor.nickName}
                        </div>
                        <div className={'text'} key={mentor.content}>
                            {mentor.content}
                        </div>
                        <ul className={'category'} key={mentor.subject}>
                            <li>{mentor.subject}</li>
                        </ul>
                        <div className={'career'} key={mentor.current}>경력 : {mentor.current}</div>
                    </div>
                    {/*</Link>*/}
                </div>
            ))}

            {/*모달창 띄워주기*/}
            <Modal show={show} onHide={handleClose} id={'modal-container'}>
                <section className={'top-section'}>
                    <div className={'top-title'}>
                        <h1 className={'top-title-text'}>멘토 소개</h1>
                        <div className={'writer-wrapper'}>
                            <div className={'modify-btn'}>수정</div>
                            <div className={'delete-btn'}>삭제</div>
                        </div>
                    </div>

                    <div className={'close-btn'} onClick={handleClose}><TfiClose/></div>
                </section>

                <section className={'writer-section'}>
                    <div className={'detail-profile-img'}></div>
                    <div className={'writer-text-wrapper'}>
                        <h2 className={'detail-writer'}>홍길동</h2>
                        <h3 className={'detail-sub-title'}>프론트엔드에 고민이 있으신가요?</h3>
                        <div className={'etc-wrapper'}>
                            <div className={'member-count'}><p className={'detail-sub-text'}>인원</p>3명 모집</div>
                            <div className={'subject'}><p className={'detail-sub-text'}>인원</p>프론트엔드</div>
                            <div className={'career'}><p className={'detail-sub-text'}>인원</p>3년</div>
                            <div className={'current'}><p className={'detail-sub-text'}>인원</p>Rookies</div>
                        </div>
                    </div>
                </section>

                <section className={'main-section'}>
                    <div className={'main-section-text'}>
                        주제 : 프론트엔드에 대한 모든 고민 상담<br />
                        위치 : 온라인 (Zoom) 또는 어플 내 채팅<br />
                        날짜 : 조율<br />
                        시간 : 오후 8시 이후 가능<br />
                        <br />
                        👩🏻‍🏫 멘토링 가능 분야<br />
                        효과적인 비전공 개발자 학습 로드맵<br />
                        이력서 첨삭 및 백엔드 개발자 면접 준비<br />
                        단기간에 네카라로 취업 / 이직하기 위한 팁<br />
                        <br />
                        🔎 멘토링 진행방식<br />
                        구글밋을 통한 온라인 커피챗 형식<br />
                        환영 공지를 통해 사전 질문 구글 폼 주소 전달<br />
                        사전 질문을 기반으로 한 멘토링 진행<br />
                        시간 조율 가능하니 편하게 말씀해 주세요!<br />
                    </div>
                </section>
                {/*<Link to={''}*/}
                <div className={'btn-wrapper'}>
                <button className={'application-btn'}>신청하기</button>
                </div>
            </Modal>

            {/*<div>{logTest}</div>*/}
            <div>qwlekwqelkjqlk</div>
        </Common>
    );
};

export default MentorsList;