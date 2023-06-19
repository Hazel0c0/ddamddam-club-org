import React, {useState} from 'react';
import Box from "@mui/material/Box";
import Rating from "@mui/material/Rating";

const ReviewStarRating = ({reviewRating, setReviewRating}) => {
    const [rating, setRating] = useState(reviewRating); // 초기 값은 0으로 설정

    const handleRatingChange = (event, newRating) => {
        setRating(newRating);
        setReviewRating(newRating);
    };

    return (
        <div  classname={'star-container'}>
            <Box sx={{ display: 'flex', alignItems: 'center' }} >
                <Rating
                    name="reviewRating"
                    value={rating}
                    precision={0.5} // 0.5 단위로 평가
                    onChange={handleRatingChange}
                />
                {/*<span>{rating}</span>*/}
            </Box>
        </div>
    );
};

export default ReviewStarRating;