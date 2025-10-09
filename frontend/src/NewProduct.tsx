import {type FormEvent, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import type {ProductDto} from "./model/ProductDto.tsx";

export default function NewProduct(){

    const [productDto, setProductDto] = useState<ProductDto>({name: ""});

    const nav = useNavigate();

    function onAddNewProduct(e:FormEvent){
        e.preventDefault();
        axios.post("/api/products", productDto)
            .catch(e=>console.log(e));
        nav("/allProducts");
    }

    return(
        <>
            <form onSubmit={onAddNewProduct}>
                <label> Name:
                    <input onChange={e => setProductDto({name: e.target.value})}/>
                </label>
                <button type={"submit"}>Submit</button>
            </form>
        </>
    )
}