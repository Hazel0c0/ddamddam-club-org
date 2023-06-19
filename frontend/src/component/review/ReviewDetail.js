import React, {useEffect, useState} from 'react';
import './scss/ReviewDetail.scss';
import Common from "../common/Common";
import {useParams} from "react-router-dom";
import {REVIEW} from "../common/config/HostConfig";

const QnaDetail = () => {
    const {reviewIdx} = useParams();
    const [reviewQna, setDetailReview] = useState([]);
    useEffect(()=>{
        console.log(reviewIdx);
        fetch(`${REVIEW}/detail?${reviewIdx}`)
            .then((res) => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then((result) => {
                setDetailReview(result.payload);
                console.log(result.payload);
            });
    },[]);

    // /api/ddamddam/mentors/detail?mentorIdx={}
    ///api/ddamddam/qna/{boardId}
    return (
        <Common className={'qna-detail-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>Q&A</p>
                <p className={'main-sub-title'}>땀땀클럽 회원들과 개발 지식을 공유할 수 있는 공간입니다.</p>
            </div>

            {/*<section className={'main-text-wrapper'}>*/}
            {/*    <h1 className={'qna-title'}>*/}
            {/*        {detailQna.boardTitle}*/}
            {/*    </h1>*/}
            {/*    <section className={'info-detail-container'}>*/}
            {/*        <div className={'info-wrapper'}>*/}
            {/*            <div className={'category'}>*/}
            {/*                <span className={'sub-title'}>직무</span>*/}
            {/*                <span className={'sub-content'}>백엔드 개발자</span>*/}
            {/*            </div>*/}
            {/*            <div className={'category'}>*/}
            {/*                <span className={'sub-title'}>근속연수</span>*/}
            {/*                <span className={'sub-content'}>3년</span>*/}
            {/*            </div>*/}
            {/*            <div className={'category'}>*/}
            {/*                <span className={'sub-title'}>위치</span>*/}
            {/*                <span className={'sub-content'}>강남</span>*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*        <div className={'detail-wrapper'}>*/}
            {/*            <div className={'category'}>*/}
            {/*                <span className={'sub-title'}>작성일자</span>*/}
            {/*                <span className={'sub-content'}>2023.06.23</span>*/}
            {/*            </div>*/}
            {/*            <div className={'category'}>*/}
            {/*                <span className={'sub-title'}>조회수</span>*/}
            {/*                <span className={'sub-content'}>299</span>*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*    </section>*/}
            {/*    <section className={'main-content'}>*/}
            {/*        잡플래닛에 후기가 없다는건.. 규모가 작아서 회사의 채용규모 또한 작을 수 밖에 없는 것 입니다.*/}
            {/*        또한, 이와 같은 회사의 후기가 다른 사이트에 있더라도 후기의 숫자가 적을 수 밖에 없을 거구요.*/}
            {/*        이정도 수준이면 회사에 대한 판단은 충분할 듯 하며, 참고로 잡플래닛 후기는 회사에 대한 전반적인*/}
            {/*        평이외에는 큰 의미가 없습니다. 어떤 직무에서 어떤 상사와 사수를 만나느냐에 따라 회사는 천국이 되기도*/}
            {/*        하고 지옥이 되기도 하니.. 운이 사실 훨씬 중요합니다. 지원자가 알 수 있는 영역이 아니에요..*/}

            {/*        잡플래닛에 후기가 없다는건.. 규모가 작아서 회사의 채용규모 또한 작을 수 밖에 없는 것 입니다.*/}
            {/*        또한, 이와 같은 회사의 후기가 다른 사이트에 있더라도 후기의 숫자가 적을 수 밖에 없을 거구요.*/}
            {/*        이정도 수준이면 회사에 대한 판단은 충분할 듯 하며, 참고로 잡플래닛 후기는 회사에 대한 전반적인*/}
            {/*        평이외에는 큰 의미가 없습니다. 어떤 직무에서 어떤 상사와 사수를 만나느냐에 따라 회사는 천국이 되기도*/}
            {/*        하고 지옥이 되기도 하니.. 운이 사실 훨씬 중요합니다. 지원자가 알 수 있는 영역이 아니에요..*/}
            {/*    </section>*/}

            {/*    <section className={'checked-wrapper'}>*/}
            {/*        <span className={'check-title'}>도움이 되었다면?</span>*/}
            {/*        <button className={'check-btn'}>*/}
            {/*            <span className={'check'}>채택하기</span>*/}
            {/*        </button>*/}
            {/*    </section>*/}
            {/*</section>*/}
        </Common>
    );
};

export default QnaDetail;