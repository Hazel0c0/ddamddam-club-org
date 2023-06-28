// import React, {useEffect, useState} from 'react';
// import {getToken, getUserPosition, isLogin} from '../common/util/login-util';
// import {Button, Modal} from 'react-bootstrap';
// import {PROJECT} from "../common/config/HostConfig";
// import {Link} from "react-router-dom";
// import './scss/삭제예정퀵매칭.scss';
//
// const ProjectsQuickMatching = () => {
//   // 토큰
//   const ACCESS_TOKEN = getToken();
//   const headerInfo = {
//     'content-type': 'application/json',
//     'Authorization': 'Bearer ' + ACCESS_TOKEN
//   }
//   const USER_POSITION = getUserPosition();
//
//   // 퀵 매칭 모달창
//   const [quickDetail, setQuickDetail] = useState([]);
//   const [currentPage, setCurrentPage] = useState(1);
//
//   const [show, setShow] = useState(false);
//
//   // 퀵 매칭 버튼 클릭
//   const handleShow = () => {
//     setShow(true);
//     quickMatchingFetchData();
//     setCurrentPage(1); // 페이지를 1로 초기화
//   };
//
//   const handleClose = () => {
//     setShow(false);
//   };
//
//   // 퀵 매칭 데이터 불러오기
//   const quick = PROJECT + `/quick?position=${USER_POSITION}&page=${currentPage}`;
//   const quickMatchingFetchData = () => {
//     fetch(quick, {
//       method: 'GET',
//       headers: headerInfo
//     })
//         .then(res => {
//           if (res.status === 500) {
//             alert('서버 오류입니다');
//             return;
//           }
//           return res.json();
//         })
//         .then(res => {
//           if (res) {
//             console.log(res.payload.projects);
//             setQuickDetail(res.payload.projects);
//           }
//         });
//   };
//
//   // 지원자 현황
//   const renderApplicantImages = (count, imgSrc, position) => {
//     return Array(count)
//         .fill()
//         .map((_, index) => (
//             <img className={'position-img'} src={imgSrc} alt={position} style={{height: 50}} key={index}/>
//         ));
//   };
//
//   // 페이지
//   const handlePreviousPage = () => {
//     setCurrentPage(prevPage => prevPage - 1);
//   };
//
//   const handleNextPage = () => {
//     setCurrentPage(prevPage => prevPage + 1);
//   };
//
//   useEffect(() => {
//     quickMatchingFetchData()
//   }, [currentPage]);
//
//   return (
//       <>
//       <div className={'quick-wrapper'}>
//
//         {
//             isLogin &&
//             <Button className="quick-btn" onClick={handleShow}>
//               Quick Matching
//             </Button>
//         }
//
//
//         <div show={show} onHide={handleClose}
//              className={'modal-container'}
//                id="modal-container">
//           {quickDetail.map(board => (
//               <React.Fragment key={board.id}>
//                 <Modal.Header closeButton>
//                   <Modal.Title>{board.boardTitle}</Modal.Title>
//                 </Modal.Header>
//                 <Modal.Body>
//                   <ul>
//                     <li>타입: {board.projectType}</li>
//                     <li>모집 마감 : {board.offerPeriod}</li>
//                   </ul>
//                   <div className={'applicant-box'}>
//                     {renderApplicantImages(
//                         board.applicantOfFront,
//                         'https://cdn-icons-png.flaticon.com/128/5226/5226066.png',
//                         'front'
//                     )}
//                     {renderApplicantImages(
//                         board.maxFront - board.applicantOfFront,
//                         'https://cdn-icons-png.flaticon.com/128/5226/5226057.png',
//                         'front'
//                     )}
//                     {renderApplicantImages(
//                         board.applicantOfBack,
//                         'https://cdn-icons-png.flaticon.com/128/1353/1353491.png',
//                         'back'
//                     )}
//                     {renderApplicantImages(
//                         board.maxBack - board.applicantOfBack,
//                         'https://cdn-icons-png.flaticon.com/128/1353/1353367.png',
//                         'back'
//                     )}
//                   </div>
//                 </Modal.Body>
//                 <Modal.Footer>
//                   <div className={'footer-left'}>
//                     <Link to={`/projects/detail?projectIdx=${board.boardIdx}`}
//                     className={'detail-btn'}>
//                       상세보기
//                     </Link>
//                     <div className={'like-btn'}>♥ : {board.likeCount}</div>
//                   </div>
//
//                   <div className={'footer-right'}>
//                     {currentPage > 1 && (
//                         <Button variant="secondary" onClick={handlePreviousPage}>
//                           이전
//                         </Button>
//                     )}
//                     {currentPage <= 10 && (
//                         <Button variant="primary" onClick={handleNextPage}>
//                           다음
//                         </Button>
//                     )}
//                   </div>
//                 </Modal.Footer>
//               </React.Fragment>
//           ))}
//         </div>
//       </div>
//       </>
//   );
// };
//
// export default ProjectsQuickMatching;
