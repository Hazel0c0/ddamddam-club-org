import React, { useState, useEffect } from 'react';


const ProjectImage = ({projectIdx}) => {
  console.log(`[ img.js ] P.idx : ${projectIdx}`)
  const [imgUrl, setImgUrl] = useState([]);

  const file_URL = '//localhost:8181/api/ddamddam/load-file'

  const fetchFileImage = async (index) => {
    const res = await fetch(
      `${file_URL}?projectIdx=${projectIdx}&boardType=project`, {
        method: 'GET',
      });
    if (res.status === 200) {
      const fileBlob = await res.blob();
      const imgUrl = window.URL.createObjectURL(fileBlob);
      console.log(`img (${index}): ${imgUrl}`);

      // 이미지 수정
      setImgUrl((prevUrls) => {
        const updatedUrls = [...prevUrls];
        updatedUrls[index] = imgUrl;
        return updatedUrls;
      });
      console.log(`img (${index}): ${imgUrl}`);

    } else {
      const err = await res.text();
      console.log('img load error !! '+err);

      setImgUrl((prevUrls) => {
        const updatedUrls = [...prevUrls];
        updatedUrls[index] = null;
        return updatedUrls;
      });
    }
  };
  useEffect(() => {

    fetchFileImage(projectIdx);
  }, [projectIdx]);

  return (
    <div className={'project-img'}>
      <img
        src={imgUrl}
        alt="Project Image"
        className={'project-img'}
        style={{
          height: 120,
          marginBottom: 20
        }}
      />
    </div>
  );
};

export default ProjectImage;