import type {Product} from "../model/Product.tsx";
import './ProductCard.css'
import {useState} from "react";
import axios from "axios";

type ProductCardProps = {
    product: Product
}

export default function ProductCard(props: Readonly<ProductCardProps>){

    const [isEditing, setIsEditing] = useState(false);
    const [name, setName] = useState(props.product.name);

    function updateProduct() {
        axios.put(`/api/products/${props.product.id}`, {name: name})
            .then((response) => setName(response.data.name))
            .catch((e) => console.log(e))
    }

    return(
            <div className={"product"}>
                {!isEditing && <h2>{name}</h2>}
                {isEditing && <input type={"text"} value={name} onChange={(e) => setName(e.target.value)}/>}

                <h3>ID: {props.product.id}</h3>
                {!isEditing && <button onClick={() =>setIsEditing(!isEditing)}> ✎Edit</button>}
                {isEditing && <button onClick={() => {updateProduct(); setIsEditing(!isEditing) }}>Save</button>}
            </div>
    )
}