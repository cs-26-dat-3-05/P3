
function EmailBox(){

    function SendMessageHandler(e: any){
        // Can't find what the name of the type of object is ??? //
        e.preventDefault();

        const modtagerInput = document.querySelector<HTMLInputElement>("#modtagerInput")?.value;
        const emneInput = document.querySelector<HTMLInputElement>("#emneInput")?.value;
        const beskedInput = document.querySelector<HTMLTextAreaElement>("#beskedInput")?.value;

        console.log(modtagerInput);
        console.log(emneInput);
        console.log(beskedInput);

        // define for later when we have integrated frontend with backend //
    }

    return (
     <>
        <fieldset>
            <h1>Send Email</h1>
            <hr></hr>
            <div className="EmailMessage">
                <form onSubmit={SendMessageHandler}>
                    <label>
                        MODTAGER
                        <input type="text" id="modtagerInput" minLength={1} required/>
                    </label> 
                    <br></br> 
                    <label>
                        EMNE
                        <input type="text" id="emneInput" minLength={1} required/>
                    </label>   
                    <br></br> 
                    <br></br> 
                    <label>
                        BESKED
                    <textarea rows={25} cols={50} id="beskedInput" minLength={1} required></textarea>
                    </label>
                
                    <button type="submit">SEND EMAIL</button>
                </form>
            </div>
        </fieldset>
    </>
    );
}

export default EmailBox;