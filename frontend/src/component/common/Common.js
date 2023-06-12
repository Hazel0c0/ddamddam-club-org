import React from 'react';
import './scss/Common.scss';

const Common = (common) => {
    const classes = 'common ' + common.className;
    return (
        <div className={classes}>{common.children}</div>
    );

}

export default Common;