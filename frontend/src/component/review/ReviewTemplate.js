import React, {useState} from 'react';
import ReviewList from "./ReviewList";
import ReviewSearch from "./ReviewSearch";
import ReviewMain from "./ReviewMain";

const QnaTemplate = () => {
    const [searchValue, setSearchValue] = useState('');

    const handleSearchChange = (value) =>{
        setSearchValue(value);
        console.log(`Search Page에서 넘어오는 value의 값 : ${value}`)
    }

    return (
        <>
            <ReviewMain />
            <ReviewSearch onSearchChange = {handleSearchChange}/>
            <ReviewList searchValue = {searchValue}/>
        </>
    );
};

export default QnaTemplate;