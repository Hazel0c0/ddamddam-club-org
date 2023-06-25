import React, {useState} from 'react';
import {getUserPosition} from "../common/util/login-util";
import {Button, Modal} from "react-bootstrap";

const ProjectsQuickMatching = ({
                                 board,
                                 handleClose
                               }) => {
  const USER_POSITION = getUserPosition();

  // 퀵 매칭
  const [show, setShow] = useState(false);

  // 지원자 현황
  const renderApplicantImages = (count, imgSrc, position) => {
    return Array(count)
      .fill()
      .map((_, index) => (
        <img
          src={imgSrc}
          alt={position}
          style={{height: 50}}
          key={index}
        />
      ));
  };

  return (
    <>
      <Modal.Header closeButton>
        <Modal.Title>{board.boardTitle}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div>나의 포지션: {USER_POSITION}</div>
        <div>제목: {board.boardTitle}</div>

        <div>
          {renderApplicantImages(board.applicantOfFront
            , "https://cdn-icons-png.flaticon.com/128/5226/5226066.png", "front")}
          {renderApplicantImages(board.maxFront-board.applicantOfFront
            , "https://cdn-icons-png.flaticon.com/128/5226/5226057.png", "front")}
          {renderApplicantImages(board.applicantOfBack
            , "https://cdn-icons-png.flaticon.com/128/1353/1353491.png", "back")}
          {renderApplicantImages(board.maxBack-board.applicantOfBack
            , "https://cdn-icons-png.flaticon.com/128/1353/1353367.png", "back")}
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          닫기
        </Button>
        <Button variant="primary" onClick={handleClose}>
          저장
        </Button>
      </Modal.Footer>
    </>
  );
};

export default ProjectsQuickMatching;
