import React from 'react';
import {RxDoubleArrowLeft, RxDoubleArrowRight} from 'react-icons/rx'
import './PageNation.scss'
const PageNation = ({pageNation, currentPageHandler, clickCurrentPage}) => {
    const pageAmount = () => {
        const pageList = [];

        if (pageNation.currentPage !== 1) {
            pageList.push(
                <li className="page-item" onClick={() => currentPageHandler(1)}>
                    <div className="page-link start"><RxDoubleArrowLeft/></div>
                </li>)
        }
        if (pageNation.prev) {
            pageList.push(
                <li className="page-item" onClick={() => currentPageHandler(pageNation.startPage - 1)}>
                    <div className="page-link prev">prev</div>
                </li>
            )
        }
        for (let i = pageNation.startPage; i <= pageNation.endPage; i++) {
            pageList.push(
                // <li data-page-num="${i}" className="page-item" key={i}>
                <li className={`page-item ${i === clickCurrentPage ? 'active' : ''}`}
                    key={i}
                    onClick={() => currentPageHandler(i)}>
                    <div className={`page-link`}
                         >{i}</div>
                </li>
            )
        }

        if (pageNation.next) {
            pageList.push(
                <li className="page-item" onClick={() => currentPageHandler(pageNation.endPage + 1)}>
                    <div className="page-link next">next</div>
                </li>
            )
        }

        if (pageNation.currentPage !== pageNation.totalCount) {
            pageList.push(
                <li className="page-item" onClick={() => currentPageHandler(Math.floor((pageNation.totalCount) / 10 + 1))}>
                    <div className="page-link end"
                         >
                        <RxDoubleArrowRight />
                    </div>
                </li>
            )
        }

        return pageList;
    }


    return (
        <ul className={'pagination-wrapper'}>
            {/*{clickCurrentPage}*/}
            {pageAmount()}
        </ul>
    );
};

export default PageNation;