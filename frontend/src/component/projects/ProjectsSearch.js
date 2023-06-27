import React, {useState} from 'react'
import Common from "../common/Common";
import searchIcon from "../../src_assets/search-icon.png";
import './scss/ProjectSearch.scss';
import {Link, useNavigate} from "react-router-dom";
import {PROJECT} from "../common/config/HostConfig";

const ProjectsSearch = () => {
    const [searchText, setSearchText] = useState([]);
    const [searchList, setSearchList] = useState([]);
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const inputText = e.target.value;
        setSearchText(inputText);
        console.log(inputText);
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            handleSearchClick();
        }
    };

    const handleSearchClick = () => {
        fetch(PROJECT + `?keyword=${searchText}`, {
            method: 'GET'
        })
            .then(res => {
                if (res.status === 200) return res.json()
            })
            .then(data => {
                // console.log(data.projects);
                setSearchList(data.projects);
                console.log(searchList);

                // 검색 결과 페이지로 이동
                navigate(`/projects?keyword=${searchText}`);

            });

    }


    return (
        <Common className={'project-search-wrapper'}>
            <div className={'search-wrapper'}>
                <img src={searchIcon} alt={'search-icon'} className={'search-icon'}/>
                <input className={'input-btn'}
                       placeholder={'검색'}
                       name={'search'}
                       onChange={handleInputChange}
                       onKeyPress={handleKeyPress}
                ></input>
            </div>

            <div>
                <div className={'projects-write-btn'}
                     onClick={handleSearchClick}
                     style={{cursor: 'pointer'}}
                >
                    search
                </div>
            </div>
        </Common>
    )
}

export default ProjectsSearch