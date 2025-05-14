function ProductSelector({products, handleSelectChange}) {

    return(
        <select onChange={handleSelectChange}>
            {
            products.map((p) => (
                <option key={p.id} value={p.name}>
                {p.name} ${p.price}
                </option>
            ))    
            }
        </select>
    )


}

export default ProductSelector;