import React, {useState} from 'react';
import QnaMain from "./QnaMain";
import QnaSearch from "./QnaSearch";
import QnaList from "./QnaList";

const QnaTemplate = () => {
    const [searchValue, setSearchValue] = useState('');

    const handleSearchChange = (value) =>{
        setSearchValue(value);
        console.log(`Search Page에서 넘어오는 value의 값 : ${value}`)
    }

    return (
        <>
            <QnaMain />
            <QnaSearch onSearchChange = {handleSearchChange}/>
            <QnaList searchValue = {searchValue}/>
        </>
    );
};

export default QnaTemplate;