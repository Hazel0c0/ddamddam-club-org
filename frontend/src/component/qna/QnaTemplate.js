import React from 'react';
import QnaMain from "./QnaMain";
import QnaSearch from "./QnaSearch";
import QnaList from "./QnaList";

const QnaTemplate = () => {
    return (
        <>
            <QnaMain />
            <QnaSearch />
            <QnaList />
        </>
    );
};

export default QnaTemplate;