import React, {useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import Common from "../common/Common";
import Modal from 'react-bootstrap/Modal';
import {TfiClose} from 'react-icons/tfi';
import './scss/MentorsList.scss';
import {MENTOR,CHAT} from "../common/config/HostConfig";
import less from "../../src_assets/less.png";
import than from "../../src_assets/than.png";
import {Link} from "react-router-dom";


const MentorsList = ({selectedSubjects}) => {

    const [mentorsList, setMentorsList] = useState([]);
    const [pageNation, setPageNation] = useState([]);
    const [prevBtn, setPrevBtn] = useState(false);
    const [nextBtn, setNextBtn] = useState(false);

    // 모달 useState
    const [detailMember, setDetailMember] = useState([]);
    const [show, setShow] = useState(false);

    //채팅 페이지 이동
    const [chatPageIdx, setChatPageIdx] = useState("");

    //캐러셀
    // const [currentPage, setCurrentPage] = useState(1);
    const [carouselIndex, setCarouselIndex] = useState(1);

    const handlePrevious = () => {
        if (pageNation.prev === true){
            setCarouselIndex(prevIndex => prevIndex - 1);
        }
        // if (pageNation.currentPage > 1) {
        //     setCarouselIndex(prevIndex => prevIndex - 1);
        //     setPrevBtn(true);
        // }
    };

    const handleNext = () => {
        if (pageNation.next === true){
            setCarouselIndex(prevIndex => prevIndex + 1);
        }
        // if (pageNation.currentPage === pageNation.endPage) {
        //     setNextBtn(false);
        // } else {
        //     setCarouselIndex(prevIndex => prevIndex + 1);
        //     setNextBtn(true);
        // }
    };


    const handleDelete = e => {
        if (window.confirm('삭제하시겠습니까?')) {
            fetch(`${MENTOR}/${idx}`, {
                method: 'DELETE'
            })
                .then(res => res.json())
                .then(json => {
                    setMentorsList(json.mentors);
                    setPageNation(json.pageInfo);
                    handleClose(); // 모달창 닫기
                });
        } else {
            return;
        }
    };

    const handleClose = () => {
        setShow(false)
    };

    const handleShow = (e) => {
        setShow(true)
        const detailIdx = e.target.closest('.mentors-list').querySelector('.member-idx').value

        fetch(MENTOR + '/detail?mentorIdx=' + detailIdx)
            .then(res => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then(result => {
                setDetailMember(result);
                // console.log(result);
                setChatPageIdx(result.idx);
                // console.log(result.idx);
            });
    };
    const createChatRoom = e => {
        const data = {
            senderId: 1,
            receiverId: 2,
            mentorIdx: chatPageIdx
        };

        fetch(CHAT+'/rooms', {
            method: 'POST',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify(data)
        })
            .then(res => res.json())
            .then(json => {
                alert('채팅방 생성 완료! 멘토와 즐거운 채팅~');
            })
    }

    const {title, content, subject, current, nickName, date, mentee, career, idx} = detailMember;


    // 첫 렌더링 시 출력
    useEffect(() => {
        let subjectsParam = '';
        if (selectedSubjects !== null) {
            subjectsParam = selectedSubjects.join(',');
        }


        // console.log(`subjectsParam : ${subjectsParam}`)
        fetch(MENTOR + `/sublist?page=${carouselIndex}&size=9&subjects=${subjectsParam}`)
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

                    // if (pageNation.currentPage === pageNation.endPage){
                    //     setNextBtn(false);
                    // }else {
                    //     setCarouselIndex(prevIndex => prevIndex + 1);
                    //     setNextBtn(true);
                    // }
                    console.log(`result.pageInfo : ${result.pageInfo.prev}`);
                }
            });
    }, [selectedSubjects, carouselIndex]);
    const subStringContent = (str, n) => {
        return str?.length > n
            ? str.substr(0, n - 1) + "..."
            : str;
    }


    return (
        <div className={'mentors-list-wrapper'}>
            {/*{prevBtn &&*/}
            {pageNation.prev &&
                <img src={less} alt={"less-icon"} className={'less-icon'} onClick={handlePrevious}/>
            }
            {/*{nextBtn &&*/}
            {pageNation.next &&
                <img src={than} alt={"than-icon"} className={'than-icon'} onClick={handleNext}/>
            }
            {mentorsList.map((mentor, index) => (
                // <div className={'mentors-list'} key={mentor.idx} onClick={handleShow}>
                <div className={`mentors-list ${index === carouselIndex ? 'active' : ''}`} key={mentor.idx}
                     onClick={handleShow}>
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
                            {subStringContent(mentor.content, 55)}
                            {/*{mentor.content}*/}
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
                            <div className={'delete-btn'} onClick={handleDelete}>삭제</div>
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

                <div className={'btn-wrapper'}>
                    <Link to={`/mentors/detail/chat/${chatPageIdx}`} onClick={createChatRoom}>
                        <button className={'application-btn'}>신청하기</button>
                    </Link>
                </div>
            </Modal>
        </div>
    );
};

export default MentorsList;