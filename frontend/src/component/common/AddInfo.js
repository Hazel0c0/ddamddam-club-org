import React, {useEffect, useRef} from 'react';
import logo from "../../src_assets/logo(white).png";
import mentorMain from "../../src_assets/add-info/mentor-main.png";
import mentorChat from "../../src_assets/add-info/mentor-chat.png";
import projectMain from "../../src_assets/add-info/project-main.png";
import arrowDetail from "../../src_assets/add-info/arrow.png";
import projectDetail from "../../src_assets/add-info/project-detail.png";
import review from "../../src_assets/add-info/review.png";
import company from "../../src_assets/add-info/company.png";
import qna from "../../src_assets/add-info/qna.png";
import {TfiArrowUp} from "react-icons/tfi";


const AddInfo = () => {
    const $mentorMain = useRef(null)
    const $mentorDetail = useRef(null)
    const $projectMain = useRef(null)
    const $arrowDetail = useRef(null)
    const $projectDetail = useRef(null)

    useEffect(() => {
        const options = {
            threshold: 0.6
        };

        const callback = (entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    if (entry.target === $mentorMain.current) {
                        $mentorMain.current.style.animation = 'fadeInAnimation 0.5s ease-in';
                        $mentorMain.current.style.opacity = '1';
                        // console.log(`setTimeOut 발생`)

                    } else if (entry.target === $mentorDetail.current) {
                        setTimeout(() => {
                            $mentorDetail.current.style.animation = 'fadeInAnimation 0.5s ease-in';
                            $mentorDetail.current.style.opacity = '1';
                            // console.log(`멘토 디테일 이벤트 발생`)
                        }, 500)
                        //프로젝트 fade 이벤트
                    } else if (entry.target === $projectMain.current) {
                        setTimeout(() => {
                            $projectMain.current.style.animation = 'fadeInCenterAnimation 0.5s ease-in';
                            $projectMain.current.style.opacity = '1';
                            // console.log(`프로젝트 메인 이벤트 발생`)
                        }, 0)
                    } else if (entry.target === $arrowDetail.current) {
                        setTimeout(() => {
                            $arrowDetail.current.style.animation = 'fadeInCenterAnimation 0.5s ease-in';
                            $arrowDetail.current.style.opacity = '1';
                        }, 300)
                    } else if (entry.target === $projectDetail.current) {
                        setTimeout(() => {
                            $projectDetail.current.style.animation = 'fadeInCenterAnimation 0.5s ease-in';
                            $projectDetail.current.style.opacity = '1';
                        }, 500)
                    }
                    observer.unobserve(entry.target)
                }
            })
        };

        const observer = new IntersectionObserver(callback, options);

        if ($mentorMain.current) {
            observer.observe(($mentorMain.current))
        }
        if ($mentorDetail.current) {
            observer.observe(($mentorDetail.current))
        }
        if ($projectMain.current) {
            observer.observe(($projectMain.current))
        }
        if ($arrowDetail.current) {
            observer.observe(($arrowDetail.current))
        }
        if ($projectDetail.current) {
            observer.observe(($projectDetail.current))
        }

        return () => {
            observer.disconnect();
        }

    }, [])

    const scrollTop = () =>{
        window.scrollTo(0,0);
    }

    return (
        <section>
            <div id={'add-info-wrapper'}>

                {/* 메인 영역 */}
                <div className={'add-main'}>
                    <img src={logo} alt={"로고"} className={'logo'}/>
                    <span className={'main-text'}>
                            ;;CLUB은 초급 개발자들을 중심으로 한 개발자 커뮤니티 공간을 제공합니다.<br/>
                            초급 개발자들이 <span className={'highlight'}>소외되지 않고 필요한 서비스를 간편하게 이용하는 것을 서비스의 목표</span>로 제작했습니다.<br/>
                            회원 간 자유로운 소통을 기반으로, 개발 공부를 함께 하고 취업에 대한 이야기를 나눌 수 있습니다.<br/>
                            ;;CLUB의 회원이 되어 같이 소통해보실래요? 😉
                        </span>
                </div>


                {/* 멘토 영역 */}
                <div className={'add-mentor'}>
                    <span className={'number'}>1</span>
                    <span className={'main-title'}>멘토・멘티</span>
                    <span className={'sub-text'}>
                           멘토 멘티 프로그램은 경험과 지식을 공유하고 지원하는<br/>
                            관계를 구축하는 데 중점을 둔 프로그램입니다.
                        </span>

                    <div className={'img-wrapper'}>
                        <img src={mentorMain} alt={'멘토 메인'} className={'mentorMain'} ref={$mentorMain}/>
                        <img src={mentorChat} alt={'멘토 채팅'} className={'mentorChat'} ref={$mentorDetail}/>
                    </div>
                </div>

                {/* 프로젝트 영역 */}
                <div className={'add-project'}>
                    <div className={'text-wrapper'}>
                        <span className={'number'}>2</span>
                        <span className={'main-title'}>프로젝트</span>
                        <span className={'sub-text'}>
                                취업 준비 중인 사람들에게 가치 있는 정보와 조언을 제공하며,<br/>
                                취업에 대한 현실적인 전망과 도전에 대한 인사이트를 제시합니다.
                            </span>
                    </div>

                    <div className={'img-wrapper'}>
                        <img src={projectMain} alt={'프로젝트 메인'} className={'projectMain'} ref={$projectMain}/>
                        <img src={arrowDetail} alt={'화살표'} className={'arrowDetail'} ref={$arrowDetail}/>
                        <img src={projectDetail} alt={'프로젝트 디테일'} className={'projectDetail'} ref={$projectDetail}/>
                    </div>
                </div>

                {/* 취업후기, 채용공고 영역 */}
                <div className={'add-company'}>


                    <div className={'review-wrapper'}>
                        <div className={'text-wrapper'}>
                            <span className={'number'}>3</span>
                            <span className={'main-title'}>취업후기</span>
                            <span className={'sub-text-small'}>취업 준비 중인 사람들에게 가치 있는 정보와 조언을 제공하며,<br/>
                                   취업에 대한 현실적인 전망과 도전에 대한 인사이트를 제시합니다.
                                </span>
                        </div>
                        <img src={review} alt={'취업 후기'} className={'review'}/>
                    </div>


                    <div className={'company-wrapper'}>
                        <div className={'text-wrapper'}>
                            <span className={'number'}>4</span>
                            <span className={'main-title'}>채용공고</span>
                            <span className={'sub-text-small'}>취업 준비 중인 사람들에게 가치 있는 정보와 조언을 제공하며,<br/>
                                    취업에 대한 현실적인 전망과 도전에 대한 인사이트를 제시합니다.
                                </span>
                        </div>
                        <img src={company} alt={'채용공고'} className={'company'}/>
                    </div>
                </div>

                {/* QNA */}
                <div className={'add-qna'}>
                    <div className={'text-wrapper'}>
                        <span className={'number'}>5</span>
                        <span className={'main-title'}>QNA</span>
                        <span className={'sub-text'}>
                           지식을 공유하고 도움을 주고받을 수 있는 공간입니다.<br/>
                           다양한 주제와 기술에 관련된 질문과 답변을 올릴 수 있으며,<br/>
                           커뮤니티의 활발한 참여를 통해 실무에서 겪는 문제를 해결하는 데<br/>
                           도움을 줍니다.
                            </span>
                    </div>
                    <img src={qna} alt={'프로젝트 디테일'} className={'qna'}/>

                </div>

            </div>
            <button className={'go-top-btn'} onClick={scrollTop}>
                <TfiArrowUp className={'top-arrow'}/>
            </button>
        </section>
    );
};

export default AddInfo;