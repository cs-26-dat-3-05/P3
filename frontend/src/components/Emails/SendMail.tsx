import styled from "styled-components";


const EmailSendBox = styled.div`
    border: var(--border);
    background-color: var(--background-color);
    color: var(--primary-color);
    padding: 24px;
    width: 480px;
    max-width: 100%;
    min-height: 620px;
    margin-left: auto;
    margin-top: 30px;
   
    h3 {
        margin-top: 0;
    }

    hr {
        margin-bottom: 20px;
    }

    label {
        font-size: 14px;
        display: flex;
        flex-direction: column;
        gap: 6px;
        margin-bottom: 10px;
        font-size: 12px;
    }

    input, textarea{
        box-sizing: border-box;
        border: var(--border-sm);
        background-color: var(--background-color);
        box-shadow: var(--shadow-sm);
        padding: 10px;
        font-size: 14px;
    }

    textarea{
        height: 180px;
        resize: vertical;
    }

 
    button{
        width: 100%;
        padding: 14px;
        background-color: var(--primary-color);
        color: white;
        border: var(--border-sm);
        box-shadow: var(--shadow-sm);
        cursor: pointer;
        margin-top: 10px;
    }
`


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
     <EmailSendBox className="EmailMessage">
            <h3>Send Email</h3>
            <hr></hr>
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
                    <label>
                        BESKED
                    <textarea rows={25} cols={45} id="beskedInput" minLength={1} required></textarea>
                    </label>
                
                    <button type="submit">SEND EMAIL</button>
                </form>
    </EmailSendBox>
    </>
    );
}

export default EmailBox;