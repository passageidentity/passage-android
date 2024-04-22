import os

# Define a list of tuples for replacements where each tuple is ('old_text', 'new_text')
replacements = [
    
    # Authenticator attachment "cross-platform" gets misleading name "crossMinusPlatform":
    ('crossMinusPlatform', 'crossPlatform'),

    # appleOauth2Callback functions wrongfully assume "error" is NOT null:
    ('"error" to PartConfig(body = error.value, headers = mutableMapOf())', '"error" to PartConfig(body = error?.value, headers = mutableMapOf())')

    # Add more replacements here as needed
    # ('oldText', 'newText'),
]

def replace_in_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        content = file.read()

    updated_content = content
    for old_text, new_text in replacements:
        updated_content = updated_content.replace(old_text, new_text)
    
    if content != updated_content:
        with open(file_path, 'w', encoding='utf-8') as file:
            file.write(updated_content)
        print(f"Updated {file_path}")

def main(directory_path):
    for root, dirs, files in os.walk(directory_path):
        for file in files:
            if file.endswith('.kt'):
                file_path = os.path.join(root, file)
                replace_in_file(file_path)

if __name__ == "__main__":
    # Specify the directory where your Kotlin files are located
    directory_path = './generated/src/main/kotlin/id/passage/android/'
    main(directory_path)
