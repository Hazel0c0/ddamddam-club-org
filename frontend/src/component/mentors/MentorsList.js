import React, {useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import Common from "../common/Common";
import Modal from 'react-bootstrap/Modal';
import {TfiClose} from 'react-icons/tfi';
import './scss/MentorsList.scss';
import {MENTOR} from "../common/config/HostConfig";


const MentorsList = () => {

    const [mentorsList, setMentorsList] = useState([]);
    const [pageNation, setPageNation] = useState([]);

    // 모달 useState
    const [detailMember, setDetailMember] = useState([]);
    const [show, setShow] = useState(false);

    //리다이렉트

    const handleClose = () => {
        setShow(false)

    };
    const handleShow = (e) => {
        setShow(true)
        const detailIdx = e.target.closest('.mentors-list').querySelector('.member-idx').value

        fetch(MENTOR+'/detail?mentorIdx=' + detailIdx)
            .then(res => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then(result => {
                setDetailMember(result);
                console.log(result)
            });

    };

    const {title, content, subject, current, nickName, date, mentee, career} = detailMember;


    // fetch('http://localhost:8181/api/ddamddam/mentors/list?page=&size=&sort=')
    useEffect(() => {
        fetch(MENTOR + '/list?page=1&size9=&sort=mentorDate')
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

    // http://localhost:8181/api/ddamddam/mentors/detail?mentorIdx=1
    return (
        <Common className={'mentors-list-wrapper'}>
            {mentorsList.map((mentor) => (
                <div className={'mentors-list'} key={mentor.idx} onClick={handleShow}>
                    <input type={'hidden'} value={mentor.idx} className={'member-idx'}/>

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
                        <div className={'career'} key={mentor.current}>경력 : {mentor.career}</div>
                    </div>
                    {/*</Link>*/}
                </div>
            ))}

            {/*모달창 띄워주기*/}
            <Modal show={show} onHide={handleClose} id={'modal-container'}>
                <section className={'top-section'}>
                    <div className={'top-title'}>
                        <h1 className={'top-title-text'}>멘토 소개</h1>
                        <div className={'write-date'}>{date}</div>

                        {/* 조건문 작성 필요 */}
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
                        <h2 className={'detail-writer'}>{nickName}</h2>
                        <h3 className={'detail-sub-title'}>{title}</h3>
                        <div className={'etc-wrapper'}>
                            <div className={'member-count'}><p className={'detail-sub-text'}>인원</p>{mentee}명 모집</div>
                            <div className={'subject'}><p className={'detail-sub-text'}>주제</p>{subject}</div>
                            <div className={'career'}><p className={'detail-sub-text'}>경력</p>{career}</div>
                            <div className={'current'}><p className={'detail-sub-text'}>현직</p>{current}</div>
                        </div>
                    </div>
                </section>

                <section className={'main-section'}>
                    <div className={'main-section-text'}>
                        {content}
                    </div>
                </section>
                {/*<Link to={''}*/}
                <div className={'btn-wrapper'}>
                    <button className={'application-btn'}>신청하기</button>
                </div>
            </Modal>
        </Common>
    );
};

export default MentorsList;