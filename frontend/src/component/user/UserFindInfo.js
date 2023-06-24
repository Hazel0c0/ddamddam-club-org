import React from 'react';

const UserFindInfo = () => {
  return (
    <div className={'form-container'}>
      <section className={'form-wrapper'} onSubmit={handleSubmit}>
        <br/>
        ** 분실하지 않도록 신중하게 설정하세요 ! **
        <br/><br/><br/>
        <div className={'input-nickname'}>
          <label className={'id'}>
            사용자 아이디(이메일) :
            <input
              type="text"
              name="nickname"
              className={'nickname'}
              value={userIdx}
              onChange={handleInputChange}
            />
            <span className={correct.userNickName ? 'correct' : 'not-correct'}>{message.userNickName}</span>
          </label>
        </div>
        <br/><br/>
        <label className={'oldpw'}>
          기존 비밀번호 :
          <input
            type="password"
            name="newPassword"
            className={'oldpassword-input'}
            value={newPassword}
            onChange={handleInputChange}
          />
        </label>
        <br/><br/>
        <label className={'newpw'}>
          새로운 비밀번호 :
          <input
            type="password"
            name="newPassword"
            className={'newpassword-input'}
            value={newPassword}
            onChange={handleInputChange}
          />
        </label>
        <br/><br/>
        <label className={'newpw-check'}>
          비밀번호 확인 :
          <input
            type="password"
            name="newPassword"
            className={'passwordcheck-input'}
            value={newPassword}
            onChange={handleInputChange}
          />
        </label>
        <br/><br/>
        <button className={'changebtn'} type="submit">변경하기</button>
      </section>
    </div>
  );
};

export default UserFindInfo;