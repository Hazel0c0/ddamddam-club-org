import React from 'react';
import Common from "../common/Common";
import './scss/QnaMain.scss'
import viewIcon from '../../src_assets/view-icon.png';
import speechBubble from '../../src_assets/speech-bubble.png';

const QnaMain = () => {
    return (
        <Common className={'qna-view-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>Q&A</p>
                <p className={'main-sub-title'}>땀땀클럽 회원들과 개발 지식을 공유할 수 있는 공간입니다.</p>
            </div>

            <section className={'top-view-wrapper'}>
                <div className={'top-section-one'}>
                    <h1 className={'top-section-title'}>🔥 주간 조회수 TOP1 🔥</h1>
                    <section className={'top-section-wrapper'}>
                        <div className={'checked'}>채택 완료</div>
                        <div className={'qna-title'}>질문이요요질문이요질문이요...</div>
                        <div className={'qna-content'}>질문 본문입니다질문 본문입니다질문 본문입니다질문 본문입니다질문 본문입니다...</div>
                        <div className={'icon-wrapper'}>
                            <div className={'view-count-wrapper'}>
                                <img src={viewIcon} alt={'view-count'} className={'view-count-icon'}/><span>10</span>
                            </div>
                            <div className={'speech-count-wrapper'}>
                                <img src={speechBubble} alt={'view-count'}
                                     className={'speech-count-icon'}/><span>10</span>
                            </div>
                        </div>
                    </section>
                </div>

                <div className={'top-section-two'}>
                    <h1 className={'top-section-title'}>🔥 주간 조회수 TOP2 🔥</h1>
                    <section className={'top-section-wrapper'}>
                        <div className={'not-checked'}>미채택</div>
                        <div className={'qna-title'}>질문이요요질문이요질문이요...</div>
                        <div className={'qna-content'}>질문 본문입니다질문 본문입니다질문 본문입니다질문 본문입니다질문 본문입니다...</div>
                        <div className={'icon-wrapper'}>
                            <div className={'view-count-wrapper'}>
                                <img src={viewIcon} alt={'view-count'} className={'view-count-icon'}/><span>10</span>
                            </div>
                            <div className={'speech-count-wrapper'}>
                                <img src={speechBubble} alt={'view-count'}
                                     className={'speech-count-icon'}/><span>10</span>
                            </div>
                        </div>
                    </section>
                </div>

                <div className={'top-section-three'}>
                    <h1 className={'top-section-title'}>🔥 주간 조회수 TOP3 🔥</h1>
                    <section className={'top-section-wrapper'}>
                        <div className={'not-checked'}>미채택</div>
                        <div className={'qna-title'}>질문이요요질문이요질문이요...</div>
                        <div className={'qna-content'}>질문 본문입니다질문 본문입니다질문 본문입니다질문 본문입니다질문 본문입니다...</div>
                        <div className={'icon-wrapper'}>
                            <div className={'view-count-wrapper'}>
                                <img src={viewIcon} alt={'view-count'} className={'view-count-icon'}/><span>10</span>
                            </div>
                            <div className={'speech-count-wrapper'}>
                                <img src={speechBubble} alt={'view-count'}
                                     className={'speech-count-icon'}/><span>10</span>
                            </div>
                        </div>
                    </section>
                </div>
            </section>
        </Common>
    );
};

export default QnaMain;