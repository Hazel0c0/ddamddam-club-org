import React from 'react';

const ProjectImage = ({ imageUrl }) => {
  return (
    <div className={'project-img'}>
      <img
        src={imageUrl}
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