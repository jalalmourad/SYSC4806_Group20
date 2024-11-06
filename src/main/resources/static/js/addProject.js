document.addEventListener('DOMContentLoaded', (event) =>
{
    // Selects the form element by its ID
    const newProjectForm = document.getElementById('new-project-form');

    // Adds a submit event listener to the form
    newProjectForm.addEventListener('submit', function(event)
    {
        event.preventDefault(); // Prevents the default form submission behavior

        // Creates a FormData object from the form
        const formData = new FormData(newProjectForm);

        // Remove program-restrictions entries from formData to prevent duplication
        formData.delete('program-restrictions');

        // Gather checked program restrictions
        const programRestrictions = [];
        document.querySelectorAll('input[name="program-restrictions"]:checked').forEach((checkbox) => {
            programRestrictions.push(checkbox.value);
        });

        // Convert the form data to an object
        const dataObject = Object.fromEntries(formData);
        //Adds programRestrictions array
        dataObject['programRestrictions'] = programRestrictions;
        //Convert to JSON
        const jsonData = JSON.stringify(dataObject);

        // Send JSON data to the server
        //Path needs to be changed to align with controller
        fetch('/newProject', {
            method: 'POST',
            body: jsonData,
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if(!response.ok)
                {
                    throw new Error('Network response failed')
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                alert('Project Successfully Saved');

                // Redirects to the view projects page
                //Path needs to be updated with view project's page path
                window.location.href = '../viewProjects.html';
            })
            .catch(error => {
                console.error('Error:', error);
                alert('There was a problem with saving new project. Please try again.')
            })
    })
})